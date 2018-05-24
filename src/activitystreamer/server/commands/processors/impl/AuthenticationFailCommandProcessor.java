package activitystreamer.server.commands.processors.impl;

import activitystreamer.command.Command;
import activitystreamer.commands.AuthenticationFailCommand;
import activitystreamer.server.Connection;

public class AuthenticationFailCommandProcessor extends AbstractServerCommandProcessor<AuthenticationFailCommand> {

	@Override
	public Command processCommand(AuthenticationFailCommand command, Connection aConnection) {
		aConnection.setShouldClose(true);
		return null;
	}

}
