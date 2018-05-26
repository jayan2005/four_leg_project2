package activitystreamer.commands;

import org.json.simple.JSONObject;

import activitystreamer.command.Command;
import activitystreamer.commands.AbstractCommand;

public class ActivityBroadcastCommand extends AbstractCommand {

	private JSONObject activity;
	private String serverId;
	private long messageTime;
	private long messageId;

	public ActivityBroadcastCommand(JSONObject activity,String serverId, long messageId,  long messageTime) {
		super(Command.Names.ACTIVITY_BROADCAST.toString());
		this.activity = activity;
		this.serverId = serverId;
		this.messageId = messageId;
		this.messageTime = messageTime;
	}

	public JSONObject getActivity() {
		return activity;
	}

	@Override
	public boolean isBroadcast() {
		return true;
	}

	public String getServerId() {
		return serverId;
	}

	public long getMessageTime() {
		return messageTime;
	}

	public long getMessageId() {
		return messageId;
	}
	
}
