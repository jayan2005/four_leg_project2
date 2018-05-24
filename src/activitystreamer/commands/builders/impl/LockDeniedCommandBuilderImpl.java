package activitystreamer.commands.builders.impl;

import org.json.simple.JSONObject;

import activitystreamer.commands.LockDeniedCommand;
import activitystreamer.commands.builder.CommandBuilder;

public class LockDeniedCommandBuilderImpl implements CommandBuilder<LockDeniedCommand> {

	@Override
	public LockDeniedCommand buildCommandObject(JSONObject jsonObject) {
		String username = (String) jsonObject.get("username");
		String secret = (String) jsonObject.get("secret");

		LockDeniedCommand lockDeniedCommand = new LockDeniedCommand(username, secret);
		return lockDeniedCommand;
	}

}
