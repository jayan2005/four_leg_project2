package activitystreamer.commands;

import activitystreamer.command.Command;

public class ServerAnnounceCommand extends AbstractCommand {

	private String id;
	private String secret;
	private int load;
	private String hostname;
	private int port;

	public ServerAnnounceCommand(String id, String secret, int load, String hostname, int port) {
		super(Command.Names.SERVER_ANNOUNCE.toString());
		this.id = id;
		this.secret = secret;
		this.load = load;
		this.hostname = hostname;
		this.port = port;
	}

	public String getId() {
		return id;
	}

	public int getLoad() {
		return load;
	}

	public String getHostname() {
		return hostname;
	}

	public int getPort() {
		return port;
	}

	public String getSecret() {
		return secret;
	}
	

}
