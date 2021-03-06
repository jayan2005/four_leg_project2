package activitystreamer.commands;

import activitystreamer.command.Command;
import activitystreamer.command.ResultCommand;

public class InvalidMessageCommand extends AbstractCommand implements ResultCommand {

	private String info;

	public InvalidMessageCommand(String info) {
		super(Command.Names.INVALID_MESSAGE.toString());
		this.info = info;
	}

	public String getInfo() {
		return info;
	}

}
