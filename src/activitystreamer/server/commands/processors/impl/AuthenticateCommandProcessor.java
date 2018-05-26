package activitystreamer.server.commands.processors.impl;

import java.util.Set;

import activitystreamer.command.Command;
import activitystreamer.commands.AuthenticateCommand;
import activitystreamer.commands.AuthenticationSuccessCommand;
import activitystreamer.server.Connection;
import activitystreamer.server.ServerInfo;
import activitystreamer.server.ServerInfoServiceImpl;
import activitystreamer.server.User;
import activitystreamer.server.UserServiceImpl;
import activitystreamer.util.Settings;

public class AuthenticateCommandProcessor extends AbstractServerCommandProcessor<AuthenticateCommand> {

	@Override
	public Command processCommand(AuthenticateCommand command, Connection aConnection) {
		if (aConnection.isAuthenticated()) {
			aConnection.setShouldClose(true);
			return prepareInvalidMessageCommand("already authenticated.");
		}

		if (!Settings.getSelfSecret().equals(command.getSecret())) {
			aConnection.setShouldClose(true);
			return prepareAuthenticationFailMessageCommand("secret does not match");
		}

		aConnection.setAuthenticated(true);
		aConnection.setServerId(command.getServerId());
		
		UserServiceImpl userService = UserServiceImpl.getInstance();
		Set<User> clients = userService.getAllUsers();
		ServerInfoServiceImpl serverInfoService = ServerInfoServiceImpl.getInstance();
		Set<ServerInfo> servers = serverInfoService.getAllServersInfo();
		
		AuthenticationSuccessCommand authenticationSuccessCommand = new AuthenticationSuccessCommand(Settings.getSelfId(),clients, servers);
		return authenticationSuccessCommand;
	}

}
