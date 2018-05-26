package activitystreamer.server.commands.processors.impl;

import java.util.Set;

import activitystreamer.command.Command;
import activitystreamer.commands.LoginCommand;
import activitystreamer.commands.RedirectCommand;
import activitystreamer.server.Connection;
import activitystreamer.server.Control;
import activitystreamer.server.ServerInfo;
import activitystreamer.server.ServerInfoServiceImpl;
import activitystreamer.server.UserServiceImpl;

public class LoginCommandProcessor extends AbstractServerCommandProcessor<LoginCommand> {

	@Override
	public Command processCommand(LoginCommand command, Connection aConnection) {
		boolean loginSuccess = false;

		if (aConnection.isLoggedin()) {
			aConnection.setShouldClose(true);
			return prepareInvalidMessageCommand("Cannot login again.Already logged in");
		}

		if ("anonymous".equals(command.getUsername())) {
			loginSuccess = true;
			aConnection.setLoggedin(true);
		} else {
			UserServiceImpl userService = UserServiceImpl.getInstance();
			loginSuccess = userService.login(command.getUsername(), command.getSecret());
			aConnection.setLoggedin(true);
			aConnection.setUser(userService.getUser(command.getUsername()));
		}

		if (loginSuccess) {
			Command loginSuccessCommand = prepareLoginSuccessCommand("logged in as user " + command.getUsername());
			RedirectCommand redirectCommand = checkForRedirect();
			if (redirectCommand == null) {
				return loginSuccessCommand;
			} else {
				aConnection.setShouldClose(true);
				Control.getInstance().sendCommand(aConnection, loginSuccessCommand);
				return redirectCommand;
			}
		} else {
			aConnection.setShouldClose(true);
			return prepareLoginFailedCommand("attempt to login with wrong secret");
		}
	}

	public RedirectCommand checkForRedirect() {
		Set<ServerInfo> servers = ServerInfoServiceImpl.getInstance().getAllServersInfo();
		int currentConnections = Control.getInstance().getClientConnections().size();
		for (ServerInfo serverInfo : servers) {
			if ((serverInfo.getLoad() + 2) < currentConnections) {
				return new RedirectCommand(serverInfo.getHostname(), serverInfo.getPort());
			}
		}
		return null;
	}

}
