package activitystreamer.commands;

import activitystreamer.command.Command;

public class AuthenticationFailCommand extends AbstractCommand {

	private String info;

	public AuthenticationFailCommand(String info) {
		super(Command.Names.AUTHENTICATION_FAIL.toString());
		this.info = info;
	}

	public String getInfo() {
		return info;
	}

}
