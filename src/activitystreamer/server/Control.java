package activitystreamer.server;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import activitystreamer.command.Command;
import activitystreamer.commands.AuthenticateCommand;
import activitystreamer.commands.InvalidMessageCommand;
import activitystreamer.commands.RedirectCommand;
import activitystreamer.commands.ServerAnnounceCommand;
import activitystreamer.commands.ServerQuitCommand;
import activitystreamer.commands.builder.CommandBuilder;
import activitystreamer.commands.builders.impl.CommandBuilderFactoryImpl;
import activitystreamer.commands.exceptions.InvalidCommandException;
import activitystreamer.commands.json.builder.CommandJsonBuilder;
import activitystreamer.commands.json.builders.impl.CommandJsonBuilderFactoryImpl;
import activitystreamer.commands.validators.CommandValidator;
import activitystreamer.commands.validators.impl.CommandValidatorFactory;
import activitystreamer.server.commands.processor.CommandProcessor;
import activitystreamer.server.commands.processors.impl.LockResponseListener;
import activitystreamer.server.commands.processors.impl.ServerCommandProcessorFactory;
import activitystreamer.util.Settings;

public class Control extends Thread {
	private static final Logger log = LogManager.getLogger();
	private static ArrayList<Connection> connections;
	private static boolean term = false;
	private static Listener listener;

	protected static Control control = null;
	private JSONParser jsonParser;
	private Map<String, LockResponseListener> lockResponseListeners;

	public static Control getInstance() {
		if (control == null) {
			control = new Control();
		}
		return control;
	}

	public Control() {
		// initialize the connections array
		connections = new ArrayList<Connection>();
		jsonParser = new JSONParser();
		lockResponseListeners = new HashMap<String, LockResponseListener>();

		Settings.setSelfSecret(Settings.nextSecret());
		log.info("The chosen id is " + Settings.getSelfId());
		log.info("The chosen secret is " + Settings.getSelfSecret());

		// start a listener
		try {
			listener = new Listener();
			initiateConnection();
			start();
		} catch (IOException e1) {
			log.fatal("failed to startup a listening thread: " + e1);
			System.exit(-1);
		}
	}

	public Connection initiateConnection() throws IOException {
		// make a connection to another server if remote hostname is supplied
		if (Settings.getRemoteHostname() != null) {
			try {
				Connection remoteServerConnection = outgoingConnection(
						new Socket(Settings.getRemoteHostname(), Settings.getRemotePort()));
				authenticateWithRemoteServer(remoteServerConnection);
				return remoteServerConnection;
			} catch (IOException e) {
				log.error("failed to make connection to " + Settings.getRemoteHostname() + ":"
						+ Settings.getRemotePort() + " :" + e);
				throw e;
			}
		}
		return null;
	}

	private void authenticateWithRemoteServer(Connection remoteServerConnection) {
		if (StringUtils.isNotBlank(Settings.getRemoteHostname()) && StringUtils.isNotBlank(Settings.getSecret())) {
			AuthenticateCommand authenticateCommand = new AuthenticateCommand(Settings.getSecret());
			sendCommand(remoteServerConnection, authenticateCommand);

			remoteServerConnection.setAuthenticated(true); // If the authentication fails, this flag will be reset by
															// the command processor
		}
	}

	/*
	 * Processing incoming messages from the connection. Return true if the
	 * connection should close.
	 */
	@SuppressWarnings("rawtypes")
	public boolean process(Connection con, String msg) {
		try {
			log.info("Message received is " + msg);
			JSONObject jsonObject = (JSONObject) jsonParser.parse(msg);
			CommandBuilder commandBuilder = CommandBuilderFactoryImpl.getInstance().getCommandBuilder(jsonObject);
			if (commandBuilder != null) {
				// Build the command
				Command aCommand = commandBuilder.buildCommandObject(jsonObject);

				// Validate the command
				CommandValidator<Command> commandValidator = CommandValidatorFactory.getInstance()
						.getCommandValidator(aCommand);
				if (commandValidator != null) {
					commandValidator.validateCommand(aCommand);
				}

				// Process the command
				CommandProcessor<Command> commandProcessor = ServerCommandProcessorFactory.getInstance()
						.getCommandProcessor(aCommand);
				if (commandProcessor == null) {
					sendInvalidMessage(con, "Unknown command received.");
					return true;
				}

				Command result = commandProcessor.processCommand(aCommand, con);

				if (result != null) {
					// Send the response command
					sendCommand(con, result);
				}
				return con.shouldClose();
			} else {
				sendInvalidMessage(con, "Unknown command received.");
				return true;
			}
		} catch (ParseException e) {
			sendInvalidMessage(con, "Message is not a valid json");
			return true;
		} catch (InvalidCommandException e) {
			sendInvalidMessage(con, e.getMessage());
			return true;
		}
	}

	public void sendCommand(Connection con, Command result) {
		CommandJsonBuilder<Command> commandJsonBuilder = CommandJsonBuilderFactoryImpl.getInstance()
				.getJsonBuilder(result);
		JSONObject responseJson = commandJsonBuilder.buildJsonObject(result);
		log.info("Message sent is " + responseJson.toJSONString());
		if (result.isBroadcast()) {
			for (Connection conn : getClientConnections()) {
				conn.writeMsg(responseJson.toJSONString());
			}
		} else {
			con.writeMsg(responseJson.toJSONString());
		}
	}

	public void sendCommandOnce(Connection con, Command result) {
		CommandJsonBuilder<Command> commandJsonBuilder = CommandJsonBuilderFactoryImpl.getInstance()
				.getJsonBuilder(result);
		JSONObject responseJson = commandJsonBuilder.buildJsonObject(result);
		log.info("Message sent is " + responseJson.toJSONString());
		con.writeMsg(responseJson.toJSONString());
	}

	private void sendInvalidMessage(Connection con, String message) {
		InvalidMessageCommand invalidMessageCommand = new InvalidMessageCommand(message);
		sendCommand(con, invalidMessageCommand);
	}

	/*
	 * The connection has been closed by the other party.
	 */
	public synchronized void connectionClosed(Connection con) {
		if (!term)
			connections.remove(con);
	}

	/*
	 * A new incoming connection has been established, and a reference is returned
	 * to it
	 */
	public synchronized Connection incomingConnection(Socket s) throws IOException {
		log.debug("incomming connection: " + Settings.socketAddress(s));
		Connection c = new Connection(s);
		connections.add(c);
		return c;

	}

	/*
	 * A new outgoing connection has been established, and a reference is returned
	 * to it
	 */
	public synchronized Connection outgoingConnection(Socket s) throws IOException {
		log.debug("outgoing connection: " + Settings.socketAddress(s));
		Connection c = new Connection(s);
		connections.add(c);
		return c;
	}

	@Override
	public void run() {
		log.info("using activity interval of " + Settings.getActivityInterval() + " milliseconds");
		while (!term) {
			// do something with 5 second intervals in between
			try {
				Thread.sleep(Settings.getActivityInterval());
			} catch (InterruptedException e) {
				log.info("received an interrupt, system is shutting down");
				break;
			}
			if (!term) {
				term = doServerAnnounce();
			}
		}
		log.info("closing " + connections.size() + " connections");
		// clean up
		for (Connection connection : connections) {
			connection.closeCon();
		}
		listener.setTerm(true);
	}

	private boolean doServerAnnounce() {
		List<Connection> serverConnections = getServerConnections();
		if (serverConnections.size() <= 0) {
			return false;
		}

		String selfId = Settings.getSelfId(); 
		String selfSecret = Settings.getSelfSecret();
		String hostname = Settings.getLocalHostname();
		int port = Settings.getLocalPort();
		int load = getClientConnections().size();

		ServerAnnounceCommand serverAnnounceCommand = new ServerAnnounceCommand(selfId, selfSecret, load, hostname, port);
		for (Connection con : serverConnections) {
			Control.getInstance().sendCommandOnce(con, serverAnnounceCommand);
		}
		return false;
	}

	public final void setTerm(boolean t) {
		term = t;
	}

	public final ArrayList<Connection> getConnections() {
		return connections;
	}

	public final List<Connection> getClientConnections() {
		List<Connection> clients = new ArrayList<Connection>();
		for (Connection con : connections) {
			if (con.isLoggedin()) {
				clients.add(con);
			}
		}
		return clients;
	}

	public final List<Connection> getServerConnections() {
		List<Connection> servers = new ArrayList<Connection>();
		for (Connection con : connections) {
			if (con.isAuthenticated()) {
				servers.add(con);
			}
		}
		return servers;
	}

	public void registerLockResponseListener(String username, LockResponseListener listener) {
		lockResponseListeners.put(username, listener);
	}

	public void unRegisterLockResponseListener(String username, LockResponseListener listener) {
		lockResponseListeners.remove(username);
	}

	public void publishLockResponse(String username, boolean allowLock) {
		LockResponseListener listener = lockResponseListeners.get(username);
		listener.processResponse(allowLock);
	}
	
	public LockResponseListener getLockResponseListener(String username) {
		return lockResponseListeners.get(username);
	}
	
	public void shutdownGracefully() {
		
		//get all server connections
		List<Connection> serverConnections = getServerConnections();
		ServerQuitCommand serverQuitCommand = new ServerQuitCommand(Settings.getLocalHostname(), Settings.getLocalPort(), Settings.getSecret());
		
		for(Connection con : serverConnections) {
			control.getInstance().sendCommandOnce(con, serverQuitCommand);
			con.closeCon(); // closing socket connection with the server
		}
		
		//get all the client connections
		List<Connection> clientConnections = getClientConnections();
		
		//prepare redirect command for client
		// TO-DO list
			// should check whether the remote server is online.
			// should look in the server list and assign a server 
		RedirectCommand clientRedirectCommand = new RedirectCommand(Settings.getRemoteHostname(), Settings.getRemotePort());  
		
		//send redirect command to clients
		for(Connection con : clientConnections) {
			control.getInstance().sendCommandOnce(con, clientRedirectCommand);
			con.closeCon(); // closing socket connection on the client
		}
		
	}

}