package activitystreamer.commands.builders.impl;

import org.json.simple.JSONObject;

import activitystreamer.commands.LockRequestCommand;
import activitystreamer.commands.builder.CommandBuilder;

public class LockRequestCommandBuilderImpl implements CommandBuilder<LockRequestCommand> {

	@Override
	public LockRequestCommand buildCommandObject(JSONObject jsonObject) {
		String username = (String) jsonObject.get("username");
		String secret = (String) jsonObject.get("secret");
		long requestTime = (long) jsonObject.get("requestTime");

		LockRequestCommand lockRequestCommand = new LockRequestCommand(username, secret,requestTime);
		return lockRequestCommand;
	}

}
