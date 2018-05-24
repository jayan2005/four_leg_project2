package activitystreamer.commands.builders.impl;

import org.json.simple.JSONObject;

import activitystreamer.commands.RegisterSuccessCommand;
import activitystreamer.commands.builder.CommandBuilder;

public class RegisterSuccessCommandBuilderImpl implements CommandBuilder<RegisterSuccessCommand> {

	@Override
	public RegisterSuccessCommand buildCommandObject(JSONObject jsonObject) {
		String info = (String) jsonObject.get("info");

		RegisterSuccessCommand registerSuccessCommand = new RegisterSuccessCommand(info);
		return registerSuccessCommand;
	}

}
