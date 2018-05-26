package activitystreamer.commands;

import activitystreamer.command.Command;

public class LockRequestCommand extends AbstractCommand {

	private String username;
	private String secret;
	private long requestTime;

	public LockRequestCommand(String username, String secret, long requestTime) {
		super(Command.Names.LOCK_REQUEST.toString());
		this.username = username;
		this.secret = secret;
		this.requestTime = requestTime;
	}

	public String getUsername() {
		return username;
	}

	public String getSecret() {
		return secret;
	}

	public long getRequestTime() {
		return requestTime;
	}
	
}
