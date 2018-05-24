package activitystreamer.server.commands.processors.impl;

import activitystreamer.command.Command;
import activitystreamer.commands.LockAllowedCommand;
import activitystreamer.server.Connection;
import activitystreamer.server.Control;
import activitystreamer.server.UserServiceImpl;

public class LockAllowedCommandProcessor extends AbstractServerCommandProcessor<LockAllowedCommand> {

	@Override
	public Command processCommand(LockAllowedCommand command, Connection aConnection) {
		if (!aConnection.isAuthenticated()) {
			aConnection.setShouldClose(true);
			return prepareInvalidMessageCommand(
					"Server is not yet authenticated. Authenticate first with the valid secret");
		}

		UserServiceImpl.getInstance().register(command.getUsername(), command.getSecret());

		Control.getInstance().publishLockResponse(command.getUsername(), true);
		return null;
	}
}
