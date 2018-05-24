package activitystreamer.commands.builders.impl;

import org.json.simple.JSONObject;

import activitystreamer.commands.RegisterCommand;
import activitystreamer.commands.builder.CommandBuilder;

public class RegisterCommandBuilderImpl implements CommandBuilder<RegisterCommand> {

	@Override
	public RegisterCommand buildCommandObject(JSONObject jsonObject) {
		String username = (String) jsonObject.get("username");
		String secret = (String) jsonObject.get("secret");

		RegisterCommand registerCommand = new RegisterCommand(username, secret);
		return registerCommand;
	}

}
