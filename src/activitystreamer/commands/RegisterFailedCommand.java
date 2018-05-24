package activitystreamer.commands;

import activitystreamer.command.Command;
import activitystreamer.command.ResultCommand;

public class RegisterFailedCommand extends AbstractCommand implements ResultCommand {

	private String info;

	public RegisterFailedCommand(String info) {
		super(Command.Names.REGISTER_FAILED.toString());
		this.info = info;
	}

	@Override
	public String getInfo() {
		return info;
	}

}
