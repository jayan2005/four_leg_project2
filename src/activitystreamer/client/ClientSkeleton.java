package activitystreamer.client;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;

import activitystreamer.client.ui.ClientUIManager;
import activitystreamer.commands.ActivityMessageCommand;
import activitystreamer.commands.LoginCommand;
import activitystreamer.commands.LogoutCommand;
import activitystreamer.commands.RegisterCommand;
import activitystreamer.commands.json.builder.CommandJsonBuilder;
import activitystreamer.commands.json.builders.impl.CommandJsonBuilderFactoryImpl;
import activitystreamer.util.Settings;

public class ClientSkeleton extends Thread {

	private static final Logger log = LogManager.getLogger();
	private static ClientSkeleton clientSolution;

	private Socket socket;
	private MessageListener messageListener;

	public static ClientSkeleton getInstance() {
		if (clientSolution == null) {
			clientSolution = new ClientSkeleton();
		}
		return clientSolution;
	}

	public ClientSkeleton() {
		ClientUIManager.getInstance().showLoginFrame();
		start();
	}

	public void sendRegisterCommand() {
		RegisterCommand registerCommand = new RegisterCommand(Settings.getUsername(), Settings.getSecret());
		CommandJsonBuilder<RegisterCommand> commandJsonBuilder = CommandJsonBuilderFactoryImpl.getInstance()
				.getJsonBuilder(registerCommand);
		JSONObject registerCommandJson = commandJsonBuilder.buildJsonObject(registerCommand);

		send(registerCommandJson);
	}

	public void sendLoginCommand() {
		LoginCommand loginCommand = new LoginCommand(Settings.getUsername(), Settings.getSecret());

		CommandJsonBuilder<LoginCommand> commandJsonBuilder = CommandJsonBuilderFactoryImpl.getInstance()
				.getJsonBuilder(loginCommand);
		JSONObject loginCommandJson = commandJsonBuilder.buildJsonObject(loginCommand);

		send(loginCommandJson);
	}

	public void sendActivityMessage(JSONObject obj) {
		ActivityMessageCommand activityMessageCommand = new ActivityMessageCommand(Settings.getUsername(),
				Settings.getSecret(), obj);

		CommandJsonBuilder<ActivityMessageCommand> commandJsonBuilder = CommandJsonBuilderFactoryImpl.getInstance()
				.getJsonBuilder(activityMessageCommand);
		JSONObject activityMessageCommandJson = commandJsonBuilder.buildJsonObject(activityMessageCommand);

		send(activityMessageCommandJson);
	}

	public void sendLogoutCommand() {
		LogoutCommand logoutCommand = new LogoutCommand();
		CommandJsonBuilder<LogoutCommand> commandJsonBuilder = CommandJsonBuilderFactoryImpl.getInstance()
				.getJsonBuilder(logoutCommand);
		JSONObject logoutCommandJson = commandJsonBuilder.buildJsonObject(logoutCommand);

		stopMessageListener();

		send(logoutCommandJson);

		Settings.setUsername(null);
		Settings.setSecret(null);
		Settings.setConnectionRedirect(false);
	}

	public void send(JSONObject request) {
		try {
			initializeSocket();
			if (socket != null && !socket.isClosed()) {
				// Output and Input Stream
				DataOutputStream output = new DataOutputStream(socket.getOutputStream());

				log.debug(request.toJSONString());

				// Send RMI to Server
				output.write(request.toJSONString().getBytes());
				output.write("\r".getBytes());
				output.flush();
			}
		} catch (IOException e) {
			ClientUIManager.getInstance().showErrorDialog(e.getMessage(), "Connection error");
			ClientUIManager.getInstance().handleIOError();
		}
	}

	private void initializeSocket() {
		if (socket == null || socket.isClosed()) {
			socket = openSocket();
			if (socket != null) {
				startMessageListener();
			}
		}
	}

	public void closeSocket() {
		try {
			socket.close();
			socket = null;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Socket openSocket() {
		log.debug("Host : " + Settings.getRemoteHostname());
		log.debug("port : " + Settings.getRemotePort());
		try {
			return new Socket(Settings.getRemoteHostname(), Settings.getRemotePort());
		} catch (IOException ex) {
			ClientUIManager.getInstance().showErrorDialog(ex.getMessage(), "Connection error");
			ClientUIManager.getInstance().handleIOError();
		}
		return null;
	}

	public void disconnect() {
		sendLogoutCommand();
		stopMessageListener();
		closeSocket();
		ClientUIManager.getInstance().handleLogout();
	}

	public void startMessageListener() {
		DataInputStream input;
		try {
			log.debug("Starting the message listener");
			input = new DataInputStream(socket.getInputStream());
			BufferedReader inreader = new BufferedReader(new InputStreamReader(input));
			messageListener = new MessageListener(inreader);
			messageListener.start();
			log.debug("Started the message listener");
		} catch (IOException e) {
			ClientUIManager.getInstance().showErrorDialog(e.getMessage(), "Connection error");
			ClientUIManager.getInstance().handleIOError();
		}
	}

	public void stopMessageListener() {
		if (messageListener != null) {
			log.debug("Stopping the message listener");
			messageListener.close();
			log.debug("Stopped the message listener");
		}
	}

	public void run() {

	}

}
