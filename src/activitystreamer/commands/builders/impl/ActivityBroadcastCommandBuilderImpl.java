package activitystreamer.commands.builders.impl;

import org.json.simple.JSONObject;

import activitystreamer.commands.ActivityBroadcastCommand;
import activitystreamer.commands.builder.CommandBuilder;

public class ActivityBroadcastCommandBuilderImpl implements CommandBuilder<ActivityBroadcastCommand> {

	@Override
	public ActivityBroadcastCommand buildCommandObject(JSONObject jsonObject) {
		JSONObject activity = (JSONObject) jsonObject.get("activity");
		String serverId = (String) jsonObject.get("serverId");
		long messageId = (long) jsonObject.get("seqNo");
		long messageTime = (long) jsonObject.get("timestamp");
		
		ActivityBroadcastCommand activityBroadcastCommand = new ActivityBroadcastCommand(activity,serverId,messageId,messageTime);
		return activityBroadcastCommand;
	}

}
