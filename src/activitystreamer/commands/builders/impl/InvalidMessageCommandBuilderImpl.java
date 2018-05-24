package activitystreamer.commands.builders.impl;

import org.json.simple.JSONObject;

import activitystreamer.commands.InvalidMessageCommand;
import activitystreamer.commands.builder.CommandBuilder;

public class InvalidMessageCommandBuilderImpl implements CommandBuilder<InvalidMessageCommand> {

	@Override
	public InvalidMessageCommand buildCommandObject(JSONObject jsonObject) {
		String info = (String) jsonObject.get("info");

		InvalidMessageCommand invalidMessageCommand = new InvalidMessageCommand(info);
		return invalidMessageCommand;
	}

}
