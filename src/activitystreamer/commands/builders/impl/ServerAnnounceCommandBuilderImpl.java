package activitystreamer.commands.builders.impl;

import org.json.simple.JSONObject;

import activitystreamer.commands.ServerAnnounceCommand;
import activitystreamer.commands.builder.CommandBuilder;

public class ServerAnnounceCommandBuilderImpl implements CommandBuilder<ServerAnnounceCommand> {

	@Override
	public ServerAnnounceCommand buildCommandObject(JSONObject jsonObject) {
		String id = (String) jsonObject.get("id");
		String hostname = (String) jsonObject.get("hostname");
		Long port = (Long) jsonObject.get("port");
		Long load = (Long) jsonObject.get("load");

		ServerAnnounceCommand aCommand = new ServerAnnounceCommand(id, load.intValue(), hostname, port.intValue());
		return aCommand;
	}

}
