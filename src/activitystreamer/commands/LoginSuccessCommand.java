package activitystreamer.commands;

import activitystreamer.command.Command;
import activitystreamer.command.ResultCommand;

public class LoginSuccessCommand extends AbstractCommand implements ResultCommand {

	private String info;

	public LoginSuccessCommand(String info) {
		super(Command.Names.LOGIN_SUCCESS.toString());
		this.info = info;
	}

	@Override
	public String getInfo() {
		return info;
	}

}
