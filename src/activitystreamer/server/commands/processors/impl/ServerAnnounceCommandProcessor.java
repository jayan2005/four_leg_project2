package activitystreamer.server.commands.processors.impl;

import java.util.List;

import activitystreamer.command.Command;
import activitystreamer.commands.ServerAnnounceCommand;
import activitystreamer.server.Connection;
import activitystreamer.server.Control;
import activitystreamer.server.ServerInfo;
import activitystreamer.server.ServerInfoServiceImpl;

public class ServerAnnounceCommandProcessor extends AbstractServerCommandProcessor<ServerAnnounceCommand> {

	@Override
	public Command processCommand(ServerAnnounceCommand command, Connection aConnection) {
		if (!aConnection.isAuthenticated()) {
			aConnection.setShouldClose(true);
			return prepareInvalidMessageCommand(
					"Server is not yet authenticated. Authenticate first with valid secret");
		}

		ServerInfoServiceImpl serverInfoServiceImpl = ServerInfoServiceImpl.getInstance();
		ServerInfo serverInfo = serverInfoServiceImpl.getServerInfo(command.getId());

		if (serverInfo == null) { // Register the server
			serverInfoServiceImpl.addServerInfo(command.getId(), command.getSecret(),command.getHostname(), command.getPort(),
					command.getLoad());
		} else { // Update the server
			serverInfoServiceImpl.udpateServerLoad(command.getId(), command.getLoad());
		}

		List<Connection> serverConnections = Control.getInstance().getServerConnections();
		for (Connection con : serverConnections) {
			if (!con.equals(aConnection)) {
				Control.getInstance().sendCommandOnce(con, command);
			}
		}

		return null;
	}

}
