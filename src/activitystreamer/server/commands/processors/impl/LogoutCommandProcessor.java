package activitystreamer.server.commands.processors.impl;

import activitystreamer.command.Command;
import activitystreamer.commands.LogoutCommand;
import activitystreamer.server.Connection;

public class LogoutCommandProcessor extends AbstractServerCommandProcessor<LogoutCommand> {

	@Override
	public Command processCommand(LogoutCommand command, Connection aConnection) {
		aConnection.setShouldClose(true);
		return null;
	}

}
