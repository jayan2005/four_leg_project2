package activitystreamer.commands.builders.impl;

import org.json.simple.JSONObject;

import activitystreamer.commands.AuthenticationFailCommand;
import activitystreamer.commands.builder.CommandBuilder;

public class AuthenticationFailCommandBuilderImpl implements CommandBuilder<AuthenticationFailCommand> {

	@Override
	public AuthenticationFailCommand buildCommandObject(JSONObject jsonObject) {
		String info = (String) jsonObject.get("info");

		AuthenticationFailCommand authenticationFailCommand = new AuthenticationFailCommand(info);
		return authenticationFailCommand;
	}

}
