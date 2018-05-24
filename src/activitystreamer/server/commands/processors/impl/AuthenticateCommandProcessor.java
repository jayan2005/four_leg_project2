package activitystreamer.server.commands.processors.impl;

import activitystreamer.command.Command;
import activitystreamer.commands.AuthenticateCommand;
import activitystreamer.server.Connection;
import activitystreamer.util.Settings;

public class AuthenticateCommandProcessor extends AbstractServerCommandProcessor<AuthenticateCommand> {

	@Override
	public Command processCommand(AuthenticateCommand command, Connection aConnection) {
		if (aConnection.isAuthenticated()) {
			aConnection.setShouldClose(true);
			return prepareInvalidMessageCommand("already authenticated.");
		}

		if (!Settings.getSelfSecret().equals(command.getSecret())) {
			aConnection.setShouldClose(true);
			return prepareAuthenticationFailMessageCommand("secret does not match");
		}

		aConnection.setAuthenticated(true);
		return null;
	}

}
