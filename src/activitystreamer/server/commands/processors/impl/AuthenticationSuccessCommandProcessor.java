package activitystreamer.server.commands.processors.impl;

import java.util.Set;

import activitystreamer.command.Command;
import activitystreamer.commands.AuthenticationSuccessCommand;
import activitystreamer.server.Connection;
import activitystreamer.server.ServerInfo;
import activitystreamer.server.ServerInfoServiceImpl;
import activitystreamer.server.User;
import activitystreamer.server.UserServiceImpl;

public class AuthenticationSuccessCommandProcessor extends AbstractServerCommandProcessor<AuthenticationSuccessCommand> {

	@Override
	public Command processCommand(AuthenticationSuccessCommand command, Connection aConnection) {
		
		aConnection.setServerId(command.getServerId());
		UserServiceImpl userService = UserServiceImpl.getInstance();
		ServerInfoServiceImpl serverInfoService = ServerInfoServiceImpl.getInstance();
		
		Set<User> clients = command.getClients();
		for (User aUser : clients) {
			userService.register(aUser.getUsername(), aUser.getSecret());
		}
		
		Set<ServerInfo> servers = command.getServers();
		for (ServerInfo aServer : servers) {
			serverInfoService.addServerInfo(aServer);
		}
		
		return null;
	}

}
