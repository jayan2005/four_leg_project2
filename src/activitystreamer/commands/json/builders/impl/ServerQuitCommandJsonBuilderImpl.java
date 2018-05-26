package activitystreamer.commands.json.builders.impl;

import org.json.simple.JSONObject;

import activitystreamer.commands.ServerQuitCommand;

public class ServerQuitCommandJsonBuilderImpl extends AbstractCommandJsonBuilder<ServerQuitCommand> {

	@SuppressWarnings("unchecked")
	@Override
	public void populateJsonObject(JSONObject jsonObject, ServerQuitCommand aCommand) {
		jsonObject.put("hostname", aCommand.getHostname());
		jsonObject.put("port", aCommand.getPort());
		jsonObject.put("serect", aCommand.getSecret());
	}
}
