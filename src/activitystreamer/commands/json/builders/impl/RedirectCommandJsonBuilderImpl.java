package activitystreamer.commands.json.builders.impl;

import org.json.simple.JSONObject;

import activitystreamer.commands.RedirectCommand;

public class RedirectCommandJsonBuilderImpl extends AbstractCommandJsonBuilder<RedirectCommand> {

	@SuppressWarnings("unchecked")
	@Override
	public void populateJsonObject(JSONObject jsonObject, RedirectCommand aCommand) {
		jsonObject.put("hostname", aCommand.getHostname());
		jsonObject.put("port", aCommand.getPort());
	}

}
