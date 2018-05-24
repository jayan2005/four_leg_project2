package activitystreamer.commands.json.builders.impl;

import org.json.simple.JSONObject;

import activitystreamer.commands.LockAllowedCommand;

public class LockAllowedCommandJsonBuilderImpl extends AbstractCommandJsonBuilder<LockAllowedCommand> {

	@SuppressWarnings("unchecked")
	@Override
	public void populateJsonObject(JSONObject jsonObject, LockAllowedCommand aCommand) {
		jsonObject.put("username", aCommand.getUsername());
		jsonObject.put("secret", aCommand.getSecret());
	}

}
