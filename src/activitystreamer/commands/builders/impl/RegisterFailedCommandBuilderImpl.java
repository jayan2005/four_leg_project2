package activitystreamer.commands.builders.impl;

import org.json.simple.JSONObject;

import activitystreamer.commands.RegisterFailedCommand;
import activitystreamer.commands.builder.CommandBuilder;

public class RegisterFailedCommandBuilderImpl implements CommandBuilder<RegisterFailedCommand> {

	@Override
	public RegisterFailedCommand buildCommandObject(JSONObject jsonObject) {
		String info = (String) jsonObject.get("info");

		RegisterFailedCommand registerFailedCommand = new RegisterFailedCommand(info);
		return registerFailedCommand;
	}

}
