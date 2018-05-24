package activitystreamer.server.commands.processors.impl;

import activitystreamer.command.Command;
import activitystreamer.commands.LockAllowedCommand;
import activitystreamer.commands.LockDeniedCommand;
import activitystreamer.commands.LockRequestCommand;
import activitystreamer.server.Connection;
import activitystreamer.server.Control;
import activitystreamer.server.User;
import activitystreamer.server.UserServiceImpl;

public class LockRequestCommandProcessor extends AbstractServerCommandProcessor<LockRequestCommand> {

	@Override
	public synchronized Command processCommand(LockRequestCommand command, Connection aConnection) {
		boolean lockAllowed = false;
		if (!aConnection.isAuthenticated()) {
			aConnection.setShouldClose(true);
			return prepareInvalidMessageCommand(
					"Server is not yet authenticated. Authenticate first with the valid secret");
		}

		Command resultCommand = null;
		User user = UserServiceImpl.getInstance().getUser(command.getName());
		if (user != null) {
			resultCommand = new LockDeniedCommand(command.getUsername(), command.getSecret());
		} else {
			// Local check is fine. Now broadcast and wait for responses
			int noOfServers = Control.getInstance().getServerConnections().size() - 1;
			if (noOfServers == 0) {
				lockAllowed = UserServiceImpl.getInstance().register(command.getUsername(), command.getSecret());
				;
			} else {
				// Broadcast Lock Request to all the other servers
				lockAllowed = broadcastAndProcessLockRequest(command.getUsername(), command.getSecret(), false,
						aConnection);
			}
			if (lockAllowed) {
				resultCommand = new LockAllowedCommand(command.getUsername(), command.getSecret());
			} else {
				resultCommand = new LockDeniedCommand(command.getUsername(), command.getSecret());
			}
		}

		return resultCommand;
	}

}
