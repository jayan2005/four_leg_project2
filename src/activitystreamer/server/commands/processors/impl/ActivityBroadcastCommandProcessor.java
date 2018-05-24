package activitystreamer.server.commands.processors.impl;

import java.util.List;

import activitystreamer.command.Command;
import activitystreamer.commands.ActivityBroadcastCommand;
import activitystreamer.server.Connection;
import activitystreamer.server.Control;

public class ActivityBroadcastCommandProcessor extends AbstractServerCommandProcessor<ActivityBroadcastCommand> {

	@Override
	public Command processCommand(ActivityBroadcastCommand command, Connection aConnection) {
		if (!aConnection.isAuthenticated()) {
			aConnection.setShouldClose(true);
			return prepareInvalidMessageCommand(
					"Server is not yet authenticated. Authenticate first with valid secret");
		}

		List<Connection> serverConnections = Control.getInstance().getServerConnections();
		for (Connection con : serverConnections) {
			if (!con.equals(aConnection)) {
				Control.getInstance().sendCommandOnce(con, command);
			}
		}

		List<Connection> clientConnections = Control.getInstance().getClientConnections();
		for (Connection con : clientConnections) {
			Control.getInstance().sendCommandOnce(con, command);
		}

		return null;
	}

}
