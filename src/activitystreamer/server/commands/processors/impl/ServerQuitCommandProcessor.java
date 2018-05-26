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
		ServerInfo serverInfo = serverInfoServiceImpl.getServerInfo(command.getId());
		if (serverInfo != null) {
			serverInfoServiceImpl.removeServer(serverInfo); //removing server from the list
		}
		
		log.info("Server list after removing : " +  serverInfoServiceImpl.getAllServersInfo());
		
		Control.getInstance().reconnectToAnotherHealthyServer();
		
		return null;
	}

	
}
