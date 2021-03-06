package activitystreamer.server.commands.processors.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import activitystreamer.command.Command;
import activitystreamer.commands.ActivityBroadcastCommand;
import activitystreamer.server.Connection;
import activitystreamer.server.Control;

public class ActivityBroadcastCommandProcessor extends AbstractServerCommandProcessor<ActivityBroadcastCommand> {
	
	private Map<String,Long> messageSeqMap;
	
	public ActivityBroadcastCommandProcessor() {
		messageSeqMap = new HashMap<String,Long>();
	}

	@Override
	public Command processCommand(ActivityBroadcastCommand command, Connection aConnection) {
		if (!aConnection.isAuthenticated()) {
			aConnection.setShouldClose(true);
			return prepareInvalidMessageCommand(
					"Server is not yet authenticated. Authenticate first with valid secret");
		}

		Long lastMessageId = messageSeqMap.get(command.getServerId());
		if (lastMessageId != null && (++lastMessageId != command.getMessageId())) { // Out of Sequence or Duplicate message
			return null;
		} else {
			 messageSeqMap.put(command.getServerId(),command.getMessageId());
		}
		
		List<Connection> serverConnections = Control.getInstance().getServerConnections();
		for (Connection con : serverConnections) {
			if (!con.equals(aConnection)) {
				Control.getInstance().sendCommandOnce(con, command);
			}
		}

		List<Connection> clientConnections = Control.getInstance().getClientConnections();
		for (Connection con : clientConnections) {
			if (con.getStartTime() <= command.getMessageTime()) {
				Control.getInstance().sendCommandOnce(con, command);
			}
		}

		return null;
	}

}
