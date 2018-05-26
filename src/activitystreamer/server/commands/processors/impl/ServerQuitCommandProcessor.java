package activitystreamer.server.commands.processors.impl;

import activitystreamer.command.Command;
import activitystreamer.commands.AuthenticateCommand;
import activitystreamer.commands.ServerQuitCommand;
import activitystreamer.server.Connection;
import activitystreamer.server.Control;
import activitystreamer.server.ServerInfo;
import activitystreamer.server.ServerInfoServiceImpl;
import activitystreamer.util.Settings;

import java.awt.SecondaryLoop;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerQuitCommandProcessor extends AbstractServerCommandProcessor<ServerQuitCommand> {

	private static final Logger log = LogManager.getLogger();
	
	@Override
	public Command processCommand(ServerQuitCommand command, Connection aConnection) {
		
		if (!aConnection.isAuthenticated()) {
			aConnection.setShouldClose(true);
			return prepareInvalidMessageCommand(
					"Server is not yet authenticated. Authenticate first with valid secret");
		}
		
		// remove server id from the list of servers
		ServerInfoServiceImpl serverInfoServiceImpl = ServerInfoServiceImpl.getInstance();
		serverInfoServiceImpl.removeServer(command.getId()); //removing server from the list
		
		log.info("Server list after removing : " +  serverInfoServiceImpl.getAllServersInfo());
		
		reconnect(serverInfoServiceImpl);
		
		return null;
	}

	private void reconnect(ServerInfoServiceImpl serverInfoServiceImpl) {
		// reconnect to another available server in the list
		for(ServerInfo server : serverInfoServiceImpl.getAllServersInfo()) {
			
			//check if server online
			Settings.setRemoteHostname(server.getHostname());
			Settings.setRemotePort(server.getPort());
			Settings.setSecret(server.getSecret());
			
			// Check connection and auhtenticate connection
			try {
				Connection con = Control.getInstance().initiateConnection();
				if(con.isAuthenticated()) {
					break; // break loop once authentication is success
				}
			} catch (IOException e) {
				log.error("Server connection error :" + e);
				continue;
			}
		}
	}
}
