package activitystreamer.server.commands.processor;

import activitystreamer.command.Command;
import activitystreamer.server.Connection;

public interface CommandProcessor<T extends Command> {

	Command processCommand(T command, Connection aConnection);

}
