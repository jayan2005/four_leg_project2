package activitystreamer.commands.builders.impl;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import activitystreamer.commands.AuthenticationSuccessCommand;
import activitystreamer.commands.builder.CommandBuilder;
import activitystreamer.server.ServerInfo;
import activitystreamer.server.User;

public class AuthenticationSuccessCommandBuilderImpl implements CommandBuilder<AuthenticationSuccessCommand> {

	@Override
	public AuthenticationSuccessCommand buildCommandObject(JSONObject jsonObject) {
		JSONArray clientArray = (JSONArray) jsonObject.get("clients");
		JSONArray serverArray = (JSONArray) jsonObject.get("servers");
		
		Set<User> clients = new HashSet<>();
		Iterator<JSONObject> iterator = clientArray.iterator();
        while (iterator.hasNext()) {
        	JSONObject clientObj = iterator.next();
        	User aUser = new User((String)clientObj.get("username"),(String)clientObj.get("secret"));
        	clients.add(aUser);
        }
        
        Set<ServerInfo> servers = new HashSet<>();
		Iterator<JSONObject> serverIterator = serverArray.iterator();
        while (serverIterator.hasNext()) {
        	JSONObject serverObj = serverIterator.next();
        	String id = (String) serverObj.get("id");
        	String secret = (String) serverObj.get("secret");
        	String hostname = (String) serverObj.get("hostname");
        	Long port = (Long) serverObj.get("port");
        	ServerInfo aServer = new ServerInfo();
        	aServer.setHostname(hostname);
        	aServer.setPort(port.intValue());
        	aServer.setId(id);
        	aServer.setSecret(secret);
        	servers.add(aServer);
        }

		AuthenticationSuccessCommand authenticationSuccessCommand = new AuthenticationSuccessCommand(clients,servers);
		return authenticationSuccessCommand;
	}

}
