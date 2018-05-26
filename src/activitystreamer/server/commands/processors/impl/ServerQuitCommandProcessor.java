package activitystreamer.server.commands.processors.impl;

import activitystreamer.command.Command;
import activitystreamer.commands.ServerQuitCommand;
import activitystreamer.server.Connection;

public class ServerQuitCommandProcessor extends AbstractServerCommandProcessor<ServerQuitCommand> {

	private String serverID;
	
	@Override
	public Command processCommand(ServerQuitCommand command, Connection aConnection) {
		
		if (!aConnection.isAuthenticated()) {
			aConnection.setShouldClose(true);
			return prepareInvalidMessageCommand(
					"Server is not yet authenticated. Authenticate first with valid secret");
		}
		
		// remove server id from the list of servers
		serverID = command.getId();
		
		// reconnect to other existing server in the list
		
		return null;
	}
}
