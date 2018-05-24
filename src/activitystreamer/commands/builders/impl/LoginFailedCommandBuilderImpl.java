package activitystreamer.commands.builders.impl;

import org.json.simple.JSONObject;

import activitystreamer.commands.LoginFailedCommand;
import activitystreamer.commands.builder.CommandBuilder;

public class LoginFailedCommandBuilderImpl implements CommandBuilder<LoginFailedCommand> {

	@Override
	public LoginFailedCommand buildCommandObject(JSONObject jsonObject) {
		String info = (String) jsonObject.get("info");

		LoginFailedCommand loginFailedCommand = new LoginFailedCommand(info);
		return loginFailedCommand;
	}

}
