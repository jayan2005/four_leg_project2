package activitystreamer.server.commands.processors.impl;

import activitystreamer.command.Command;
import activitystreamer.commands.ServerQuitCommand;
import activitystreamer.server.Connection;

public class ServerQuitCommandProcessor extends AbstractServerCommandProcessor<ServerQuitCommand> {

	@Override
	public Command processCommand(ServerQuitCommand command, Connection aConnection) {
		
		if (!aConnection.isAuthenticated()) {
			aConnection.setShouldClose(true);
			return prepareInvalidMessageCommand(
					"Server is not yet authenticated. Authenticate first with valid secret");
		}
		
		
		
		return null;
	}
}
