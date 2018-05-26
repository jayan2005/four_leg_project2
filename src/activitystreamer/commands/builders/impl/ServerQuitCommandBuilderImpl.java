 package activitystreamer.commands.builders.impl;

import org.json.simple.JSONObject;

import activitystreamer.commands.ServerQuitCommand;
import activitystreamer.commands.builder.CommandBuilder;

public class ServerQuitCommandBuilderImpl implements CommandBuilder<ServerQuitCommand>{

	@Override
	public ServerQuitCommand buildCommandObject(JSONObject jsonObject) {
		String id = (String) jsonObject.get("id");

		ServerQuitCommand aCommand = new ServerQuitCommand(id);
		return aCommand;
	}
}
