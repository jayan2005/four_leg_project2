package activitystreamer.commands;

import activitystreamer.command.Command;
import activitystreamer.command.ResultCommand;

public class RegisterSuccessCommand extends AbstractCommand implements ResultCommand {

	private String info;

	public RegisterSuccessCommand(String info) {
		super(Command.Names.REGISTER_SUCCESS.toString());
		this.info = info;
	}

	@Override
	public String getInfo() {
		return info;
	}

}
