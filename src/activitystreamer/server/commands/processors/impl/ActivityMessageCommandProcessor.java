package activitystreamer.server.commands.processors.impl;

import java.util.List;

import activitystreamer.command.Command;
import activitystreamer.commands.ActivityBroadcastCommand;
import activitystreamer.commands.ActivityMessageCommand;
import activitystreamer.server.Connection;
import activitystreamer.server.Control;
import activitystreamer.server.User;

public class ActivityMessageCommandProcessor extends AbstractServerCommandProcessor<ActivityMessageCommand> {
	
	private long messageId;
	
	private synchronized long nextMessageId() {
		return messageId++;
	}
	
	@Override
	public Command processCommand(ActivityMessageCommand command, Connection aConnection) {
		boolean allowBroadcast = false;

		if (!aConnection.isLoggedin()) {
			aConnection.setShouldClose(true);
			return prepareAuthenticationFailMessageCommand("must send a LOGIN command first");
		}

		User loggedInUser = aConnection.getUser();
		if ("anonymous".equals(command.getUsername())) {
			if (loggedInUser == null) {
				allowBroadcast = true;
			}
		} else {
			if (loggedInUser != null) {
				allowBroadcast = loggedInUser.getUsername().equals(command.getUsername())
						&& loggedInUser.getSecret().equals(command.getSecret());
			}
		}

		if (allowBroadcast) {
			ActivityBroadcastCommand broadcastCommand = prepareActivityBroadcast(command.getActivity(),
					command.getUsername(), nextMessageId());

			List<Connection> serverConnections = Control.getInstance().getServerConnections();
			for (Connection con : serverConnections) {
				Control.getInstance().sendCommandOnce(con, broadcastCommand);
			}

			return broadcastCommand;
		} else {
			aConnection.setShouldClose(true);
			return prepareAuthenticationFailMessageCommand("username/secret does not match logged in user");
		}
	}

}
