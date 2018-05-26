package activitystreamer.commands;

import activitystreamer.command.Command;

public class AuthenticateCommand extends AbstractCommand {

	private String secret;
	private String serverId;

	public AuthenticateCommand(String serverId, String secret) {
		super(Command.Names.AUTHENTICATE.toString());
		this.secret = secret;
		this.serverId = serverId;
	}

	public String getSecret() {
		return secret;
	}

	public String getServerId() {
		return serverId;
	}
	
}
