package activitystreamer.commands;

import activitystreamer.command.Command;

public class LockAllowedCommand extends AbstractCommand {

	private String username;
	private String secret;

	public LockAllowedCommand(String username, String secret) {
		super(Command.Names.LOCK_ALLOWED.toString());
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
