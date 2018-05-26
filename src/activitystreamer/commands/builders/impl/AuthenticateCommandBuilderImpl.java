package activitystreamer.commands.builders.impl;

import org.json.simple.JSONObject;

import activitystreamer.commands.AuthenticateCommand;
import activitystreamer.commands.builder.CommandBuilder;

public class AuthenticateCommandBuilderImpl implements CommandBuilder<AuthenticateCommand> {

	@Override
	public AuthenticateCommand buildCommandObject(JSONObject jsonObject) {
		String secret = (String) jsonObject.get("secret");
		String serverId = (String) jsonObject.get("serverId");

		AuthenticateCommand authenticateCommand = new AuthenticateCommand(serverId,secret);
		return authenticateCommand;
	}

}
