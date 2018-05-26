package activitystreamer.commands.json.builders.impl;

import org.json.simple.JSONObject;

import activitystreamer.commands.ActivityBroadcastCommand;

public class ActivityBroadcastCommandJsonBuilderImpl extends AbstractCommandJsonBuilder<ActivityBroadcastCommand> {

	@SuppressWarnings("unchecked")
	@Override
	public void populateJsonObject(JSONObject jsonObject, ActivityBroadcastCommand aCommand) {
		jsonObject.put("activity", aCommand.getActivity());
		jsonObject.put("serverId", aCommand.getServerId());
		jsonObject.put("seqNo", aCommand.getMessageId());
		jsonObject.put("timestamp", aCommand.getMessageTime());
	}

}
