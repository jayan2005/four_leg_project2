package activitystreamer.commands.json.builders.impl;

import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import activitystreamer.commands.AuthenticationSuccessCommand;
import activitystreamer.server.ServerInfo;
import activitystreamer.server.User;

public class AuthenticationSuccessCommandJsonBuilderImpl extends AbstractCommandJsonBuilder<AuthenticationSuccessCommand> {

	@SuppressWarnings("unchecked")
	@Override
	public void populateJsonObject(JSONObject jsonObject, AuthenticationSuccessCommand aCommand) {
		
		JSONArray clientArray = new JSONArray();
		Set<User> clients = aCommand.getClients();
		for (User client : clients) {
			JSONObject clientObj = new JSONObject();
			clientObj.put("username", client.getUsername());
			clientObj.put("secret", client.getSecret());
			clientArray.add(clientObj);
		}
		jsonObject.put("clients", clientArray);
		
		JSONArray serverArray = new JSONArray();
		Set<ServerInfo> servers = aCommand.getServers();
		for (ServerInfo server : servers) {
			JSONObject serverObj = new JSONObject();
			serverObj.put("id", server.getId());
			serverObj.put("secret", server.getSecret());
			serverObj.put("hostname", server.getHostname());
			serverObj.put("port", server.getPort());
			serverArray.add(serverObj);
		}
		jsonObject.put("servers", serverArray);
		jsonObject.put("serverId", aCommand.getServerId());
		
	}

}
