package activitystreamer.commands.json.builders.impl;

import org.json.simple.JSONObject;

import activitystreamer.commands.ServerAnnounceCommand;

public class ServerAnnounceCommandJsonBuilderImpl extends AbstractCommandJsonBuilder<ServerAnnounceCommand> {

	@SuppressWarnings("unchecked")
	@Override
	public void populateJsonObject(JSONObject jsonObject, ServerAnnounceCommand aCommand) {
		jsonObject.put("id", aCommand.getId());
		jsonObject.put("hostname", aCommand.getHostname());
		jsonObject.put("port", aCommand.getPort());
		jsonObject.put("load", aCommand.getLoad());
	}

}
