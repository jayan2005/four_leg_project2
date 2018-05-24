package activitystreamer.commands.builders.impl;

import org.json.simple.JSONObject;

import activitystreamer.commands.LockAllowedCommand;
import activitystreamer.commands.builder.CommandBuilder;

public class LockAllowedCommandBuilderImpl implements CommandBuilder<LockAllowedCommand> {

	@Override
	public LockAllowedCommand buildCommandObject(JSONObject jsonObject) {
		String username = (String) jsonObject.get("username");
		String secret = (String) jsonObject.get("secret");

		LockAllowedCommand lockAllowedCommand = new LockAllowedCommand(username, secret);
		return lockAllowedCommand;
	}

}
