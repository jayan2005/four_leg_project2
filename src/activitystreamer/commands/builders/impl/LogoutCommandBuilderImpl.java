package activitystreamer.commands.builders.impl;

import org.json.simple.JSONObject;

import activitystreamer.commands.LogoutCommand;
import activitystreamer.commands.builder.CommandBuilder;

public class LogoutCommandBuilderImpl implements CommandBuilder<LogoutCommand> {

	@Override
	public LogoutCommand buildCommandObject(JSONObject jsonObject) {
		LogoutCommand logoutCommand = new LogoutCommand();
		return logoutCommand;
	}

}
