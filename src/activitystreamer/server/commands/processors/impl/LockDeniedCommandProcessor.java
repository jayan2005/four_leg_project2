package activitystreamer.server.commands.processors.impl;

import activitystreamer.command.Command;
import activitystreamer.commands.LockDeniedCommand;
import activitystreamer.server.Connection;
import activitystreamer.server.Control;
import activitystreamer.server.UserServiceImpl;

public class LockDeniedCommandProcessor extends AbstractServerCommandProcessor<LockDeniedCommand> {

	@Override
	public Command processCommand(LockDeniedCommand command, Connection aConnection) {
		if (!aConnection.isAuthenticated()) {
			aConnection.setShouldClose(true);
			return prepareInvalidMessageCommand(
					"Server is not yet authenticated. Authenticate first with the valid secret");
		}

		UserServiceImpl.getInstance().unregister(command.getUsername(), command.getSecret());

		Control.getInstance().publishLockResponse(command.getUsername(), false);
		return null;
	}

}
