package activitystreamer.commands;

import activitystreamer.command.Command;

public class LockDeniedCommand extends AbstractCommand {

	private String username;
	private String secret;

	public LockDeniedCommand(String username, String secret) {
		super(Command.Names.LOCK_DENIED.toString());
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
