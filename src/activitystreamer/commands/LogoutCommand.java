package activitystreamer.commands;

import activitystreamer.command.Command;

public class LogoutCommand extends AbstractCommand {

	public LogoutCommand() {
		super(Command.Names.LOGOUT.toString());
	}

}
