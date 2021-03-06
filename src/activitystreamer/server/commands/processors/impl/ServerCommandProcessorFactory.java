package activitystreamer.server.commands.processors.impl;

import java.util.HashMap;
import java.util.Map;

import activitystreamer.command.Command;
import activitystreamer.command.Command.Names;
import activitystreamer.server.commands.processor.CommandProcessor;

@SuppressWarnings("rawtypes")
public class ServerCommandProcessorFactory {

	private static ServerCommandProcessorFactory instance;
	private Map<String, CommandProcessor> processors;

	private ServerCommandProcessorFactory() {
		processors = new HashMap<String, CommandProcessor>();

		processors.put(Names.LOGIN.toString(), new LoginCommandProcessor());
		processors.put(Names.LOGOUT.toString(), new LogoutCommandProcessor());

		processors.put(Names.REGISTER.toString(), new RegisterCommandProcessor());

		processors.put(Names.ACTIVITY_MESSAGE.toString(), new ActivityMessageCommandProcessor());
		processors.put(Names.ACTIVITY_BROADCAST.toString(), new ActivityBroadcastCommandProcessor());

		processors.put(Names.AUTHENTICATE.toString(), new AuthenticateCommandProcessor());
		processors.put(Names.AUTHENTICATION_SUCCESS.toString(), new AuthenticationSuccessCommandProcessor());
		processors.put(Names.AUTHENTICATION_FAIL.toString(), new AuthenticationFailCommandProcessor());

		processors.put(Names.SERVER_ANNOUNCE.toString(), new ServerAnnounceCommandProcessor());
		processors.put(Names.SERVER_QUIT.toString(), new ServerQuitCommandProcessor());

		processors.put(Names.LOCK_REQUEST.toString(), new LockRequestCommandProcessor());
		processors.put(Names.LOCK_ALLOWED.toString(), new LockAllowedCommandProcessor());
		processors.put(Names.LOCK_DENIED.toString(), new LockDeniedCommandProcessor());
	}

	public static ServerCommandProcessorFactory getInstance() {
		if (instance == null) {
			instance = new ServerCommandProcessorFactory();
		}
		return instance;
	}

	@SuppressWarnings("unchecked")
	public <T extends Command> CommandProcessor<T> getCommandProcessor(Command aCommand) {
		return processors.get(aCommand.getName());
	}

}
