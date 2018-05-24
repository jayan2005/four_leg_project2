package activitystreamer.commands;

import activitystreamer.command.Command;
import activitystreamer.command.ResultCommand;

public class LoginFailedCommand extends AbstractCommand implements ResultCommand {

	private String info;

	public LoginFailedCommand(String info) {
		super(Command.Names.LOGIN_FAILED.toString());
		this.info = info;
	}

	@Override
	public String getInfo() {
		return info;
	}

}
