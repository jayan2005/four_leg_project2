package activitystreamer.commands.json.builders.impl;

import org.json.simple.JSONObject;

import activitystreamer.commands.LockDeniedCommand;

public class LockDeniedCommandJsonBuilderImpl extends AbstractCommandJsonBuilder<LockDeniedCommand> {

	@SuppressWarnings("unchecked")
	@Override
	public void populateJsonObject(JSONObject jsonObject, LockDeniedCommand aCommand) {
		jsonObject.put("username", aCommand.getUsername());
		jsonObject.put("secret", aCommand.getSecret());
	}

}
