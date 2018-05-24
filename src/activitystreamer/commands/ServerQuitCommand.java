package activitystreamer.commands;

import activitystreamer.command.Command;

public class ServerQuitCommand extends AbstractCommand {
	
	private String hostname;
	private int port;
	private boolean status;

	public ServerQuitCommand(String hostname, int port, boolean status) {
		super(Command.Names.SERVER_QUIT.toString());
		this.hostname = hostname;
		this.port = port;
		this.status = status;
	}
	
	public boolean getStatus() {
		return status;
	}

	public String getHostname() {
		return hostname;
	}

	public int getPort() {
		return port;
	}
}
