package activitystreamer.commands;

import activitystreamer.command.Command;

public class AuthenticateCommand extends AbstractCommand {

	private String secret;

	public AuthenticateCommand(String secret) {
		super(Command.Names.AUTHENTICATE.toString());
		this.secret = secret;
	}

	public String getSecret() {
		return secret;
	}

}
