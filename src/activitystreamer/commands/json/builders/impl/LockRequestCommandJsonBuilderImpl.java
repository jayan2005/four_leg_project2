package activitystreamer.commands.json.builders.impl;

import org.json.simple.JSONObject;

import activitystreamer.commands.LockRequestCommand;

public class LockRequestCommandJsonBuilderImpl extends AbstractCommandJsonBuilder<LockRequestCommand> {

	@SuppressWarnings("unchecked")
	@Override
	public void populateJsonObject(JSONObject jsonObject, LockRequestCommand aCommand) {
		jsonObject.put("username", aCommand.getUsername());
		jsonObject.put("secret", aCommand.getSecret());
	}

}
