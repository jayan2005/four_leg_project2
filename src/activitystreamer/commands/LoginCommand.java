package activitystreamer.commands;

import activitystreamer.command.Command;

public class LoginCommand extends AbstractCommand {

	private String username;
	private String secret;

	public LoginCommand(String username, String secret) {
		super(Command.Names.LOGIN.toString());
		this.username = username;
		this.secret = secret;
	}

	public String getUsername() {
		return username;
	}

	public String getSecret() {
		return secret;
	}

}
