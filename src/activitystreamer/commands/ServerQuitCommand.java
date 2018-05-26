package activitystreamer.commands;

import activitystreamer.command.Command;

public class ServerQuitCommand extends AbstractCommand {
	
	private String hostname;
	private int port;
	private String secret;

	public ServerQuitCommand(String hostname, int port, String secret) {
		super(Command.Names.SERVER_QUIT.toString());
		this.hostname = hostname;
		this.port = port;
		this.secret = secret;
	}
	
	public String getSecret() {
		return secret;
	}

	public String getHostname() {
		return hostname;
	}

	public int getPort() {
		return port;
	}
}
