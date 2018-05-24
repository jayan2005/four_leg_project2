package activitystreamer.commands.builders.impl;

import org.json.simple.JSONObject;

import activitystreamer.commands.ActivityMessageCommand;
import activitystreamer.commands.builder.CommandBuilder;

public class ActivityMessageCommandBuilderImpl implements CommandBuilder<ActivityMessageCommand> {

	@Override
	public ActivityMessageCommand buildCommandObject(JSONObject jsonObject) {
		JSONObject activity = (JSONObject) jsonObject.get("activity");
		String username = (String) jsonObject.get("username");
		String secret = (String) jsonObject.get("secret");
		ActivityMessageCommand activityMessageCommand = new ActivityMessageCommand(username, secret, activity);
		return activityMessageCommand;
	}

}
