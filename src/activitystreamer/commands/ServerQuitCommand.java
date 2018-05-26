package activitystreamer.commands;

import activitystreamer.command.Command;

public class ServerQuitCommand extends AbstractCommand {
	
	private String id;

	public ServerQuitCommand(String id) {
		super(Command.Names.SERVER_QUIT.toString());
		this.id = id;
	}
	
	public String getId() {
		return this.id;
	}
}
