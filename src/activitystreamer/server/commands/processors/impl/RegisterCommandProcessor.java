package activitystreamer.server.commands.processors.impl;

import activitystreamer.command.Command;
import activitystreamer.commands.LockRequestCommand;
import activitystreamer.commands.RegisterCommand;
import activitystreamer.server.Connection;
import activitystreamer.server.Control;
import activitystreamer.server.User;
import activitystreamer.server.UserServiceImpl;

public class RegisterCommandProcessor extends AbstractServerCommandProcessor<RegisterCommand> {

	@Override
	public synchronized Command processCommand(RegisterCommand command, Connection aConnection) {
		long registerRequestTime = System.nanoTime();
		boolean registerSuccess = false;
		
		if (aConnection.isLoggedin()) {
			aConnection.setShouldClose(true);
			return prepareInvalidMessageCommand("Cannot Register.Already logged in");
		}

		User user = UserServiceImpl.getInstance().getUser(command.getUsername());

		// Already known locally. Deny quickly
		if (user != null) {
			aConnection.setShouldClose(true);
			return prepareRegisterFailedCommand(command.getUsername() + " is already registered with the system");
		}

		UserServiceImpl.getInstance().initiateUserRegistration(command.getUsername(), command.getSecret());
		int noOfServers = Control.getInstance().getServerConnections().size();
		if (noOfServers == 0) {
			registerSuccess = UserServiceImpl.getInstance().register(command.getUsername(), command.getSecret());
		} else {
			LockRequestCommand lockRequestCommand = new LockRequestCommand(command.getUsername(), command.getSecret(), registerRequestTime);
			registerSuccess = broadcastAndProcessLockRequest(lockRequestCommand, true,
					aConnection);
		}

		UserServiceImpl.getInstance().resetUserRegistration();
		if (registerSuccess) {
			return prepareRegisterSuccessCommand("register success for " + command.getUsername());
		} else {
			aConnection.setShouldClose(true);
			return prepareRegisterFailedCommand(command.getUsername() + " is already registered with the system");
		}
	}

}
