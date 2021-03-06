package activitystreamer.commands.builders.impl;

import org.json.simple.JSONObject;

import activitystreamer.commands.RedirectCommand;
import activitystreamer.commands.builder.CommandBuilder;

public class RedirectCommandBuilderImpl implements CommandBuilder<RedirectCommand> {

	@Override
	public RedirectCommand buildCommandObject(JSONObject jsonObject) {
		String hostname = (String) jsonObject.get("hostname");
		Long portLong = (Long) jsonObject.get("port");

		RedirectCommand redirectCommand = new RedirectCommand(hostname, portLong.intValue());
		return redirectCommand;
	}

}
