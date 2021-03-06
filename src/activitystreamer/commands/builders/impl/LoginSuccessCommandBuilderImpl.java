package activitystreamer.commands.builders.impl;

import org.json.simple.JSONObject;

import activitystreamer.commands.LoginSuccessCommand;
import activitystreamer.commands.builder.CommandBuilder;

public class LoginSuccessCommandBuilderImpl implements CommandBuilder<LoginSuccessCommand> {

	@Override
	public LoginSuccessCommand buildCommandObject(JSONObject jsonObject) {
		String info = (String) jsonObject.get("info");

		LoginSuccessCommand loginSuccessCommand = new LoginSuccessCommand(info);
		return loginSuccessCommand;
	}

}
