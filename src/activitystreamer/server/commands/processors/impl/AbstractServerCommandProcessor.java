package activitystreamer.server.commands.processors.impl;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.json.simple.JSONObject;

import activitystreamer.command.Command;
import activitystreamer.commands.ActivityBroadcastCommand;
import activitystreamer.commands.AuthenticationFailCommand;
import activitystreamer.commands.InvalidMessageCommand;
import activitystreamer.commands.LockRequestCommand;
import activitystreamer.commands.LoginFailedCommand;
import activitystreamer.commands.LoginSuccessCommand;
import activitystreamer.commands.RegisterFailedCommand;
import activitystreamer.commands.RegisterSuccessCommand;
import activitystreamer.server.Connection;
import activitystreamer.server.Control;
import activitystreamer.server.commands.processor.CommandProcessor;

public abstract class AbstractServerCommandProcessor<T extends Command> implements CommandProcessor<T> {

	protected InvalidMessageCommand prepareInvalidMessageCommand(String message) {
		InvalidMessageCommand invalidMessageCommand = new InvalidMessageCommand(message);
		return invalidMessageCommand;
	}

	protected LoginFailedCommand prepareLoginFailedCommand(String message) {
		LoginFailedCommand loginFailedCommand = new LoginFailedCommand(message);
		return loginFailedCommand;
	}

	protected LoginSuccessCommand prepareLoginSuccessCommand(String message) {
		LoginSuccessCommand loginSuccessCommand = new LoginSuccessCommand(message);
		return loginSuccessCommand;
	}

	protected RegisterFailedCommand prepareRegisterFailedCommand(String message) {
		RegisterFailedCommand registerFailedCommand = new RegisterFailedCommand(message);
		return registerFailedCommand;
	}

	protected RegisterSuccessCommand prepareRegisterSuccessCommand(String message) {
		RegisterSuccessCommand registerSuccessCommand = new RegisterSuccessCommand(message);
		return registerSuccessCommand;
	}

	protected AuthenticationFailCommand prepareAuthenticationFailMessageCommand(String message) {
		AuthenticationFailCommand authenticationFailCommand = new AuthenticationFailCommand(message);
		return authenticationFailCommand;
	}

	@SuppressWarnings("unchecked")
	protected ActivityBroadcastCommand prepareActivityBroadcast(JSONObject jsonObject, String username) {
		jsonObject.put("authenticated_user", username);
		ActivityBroadcastCommand activityBroadcastCommand = new ActivityBroadcastCommand(jsonObject);
		return activityBroadcastCommand;
	}

	protected int broadcastToAllServers(Command command) {
		int serverSent = 0;
		List<Connection> serverConnections = Control.getInstance().getServerConnections();
		for (Connection con : serverConnections) {
			Control.getInstance().sendCommandOnce(con, command);
			serverSent++;
		}
		return serverSent;
	}

	protected int broadcastToAllOtherServers(Command command, Connection aConnection) {
		int serverSent = 0;
		List<Connection> serverConnections = Control.getInstance().getServerConnections();
		for (Connection con : serverConnections) {
			if (!con.equals(aConnection)) {
				Control.getInstance().sendCommandOnce(con, command);
				serverSent++;
			}
		}
		return serverSent;
	}

	protected Boolean broadcastAndProcessLockRequest(LockRequestCommand lockRequestCommand, boolean allServers,
			Connection aConnection) {
		int noOfServersSent = 0;
		if (allServers) {
			noOfServersSent = broadcastToAllServers(lockRequestCommand);
		} else {
			noOfServersSent = broadcastToAllOtherServers(lockRequestCommand, aConnection);
		}

		// Collect Lock responses via Future
		LockResponseListener lockResponseListener = new LockResponseListener(lockRequestCommand.getUsername(), noOfServersSent);
		ExecutorService executorService = Executors.newSingleThreadExecutor();
		Future<Boolean> result = executorService.submit(lockResponseListener);

		Boolean lockAllowed = null;
		try {
			lockAllowed = result.get();
		} catch (InterruptedException e) {
			lockAllowed = false;
		} catch (ExecutionException e) {
			lockAllowed = false;
		}
		return lockAllowed;
	}

}
