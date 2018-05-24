package activitystreamer.commands.json.builders.impl;

import java.util.HashMap;

import java.util.Map;

import activitystreamer.command.Command;
import activitystreamer.commands.json.builder.CommandJsonBuilder;
import activitystreamer.commands.json.builder.CommandJsonBuilderFactory;

@SuppressWarnings("rawtypes")
public class CommandJsonBuilderFactoryImpl implements CommandJsonBuilderFactory {

	private static CommandJsonBuilderFactoryImpl instance;

	private Map<String, CommandJsonBuilder> builders;

	private CommandJsonBuilderFactoryImpl() {
		builders = new HashMap<String, CommandJsonBuilder>();

		builders.put(Command.Names.REGISTER.toString(), new RegisterCommandJsonBuilderImpl());
		builders.put(Command.Names.REGISTER_SUCCESS.toString(), new RegisterSuccessCommandJsonBuilderImpl());
		builders.put(Command.Names.REGISTER_FAILED.toString(), new RegisterFailedCommandJsonBuilderImpl());

		builders.put(Command.Names.LOGOUT.toString(), new LogoutCommandJsonBuilderImpl());

		builders.put(Command.Names.LOGIN.toString(), new LoginCommandJsonBuilderImpl());
		builders.put(Command.Names.LOGIN_SUCCESS.toString(), new LoginSuccessCommandJsonBuilderImpl());
		builders.put(Command.Names.LOGIN_FAILED.toString(), new LoginFailedCommandJsonBuilderImpl());

		builders.put(Command.Names.ACTIVITY_MESSAGE.toString(), new ActivityMessageCommandJsonBuilderImpl());
		builders.put(Command.Names.ACTIVITY_BROADCAST.toString(), new ActivityBroadcastCommandJsonBuilderImpl());

		builders.put(Command.Names.AUTHENTICATE.toString(), new AuthenticateCommandJsonBuilderImpl());
		builders.put(Command.Names.AUTHENTICATION_FAIL.toString(), new AuthenticationFailCommandJsonBuilderImpl());

		builders.put(Command.Names.SERVER_ANNOUNCE.toString(), new ServerAnnounceCommandJsonBuilderImpl());
		builders.put(Command.Names.SERVER_QUIT.toString(), new ServerQuitCommandJsonBuilderImpl());
		builders.put(Command.Names.INVALID_MESSAGE.toString(), new InvalidMessageCommandJsonBuilderImpl());
		builders.put(Command.Names.REDIRECT.toString(), new RedirectCommandJsonBuilderImpl());

		builders.put(Command.Names.LOCK_REQUEST.toString(), new LockRequestCommandJsonBuilderImpl());
		builders.put(Command.Names.LOCK_ALLOWED.toString(), new LockAllowedCommandJsonBuilderImpl());
		builders.put(Command.Names.LOCK_DENIED.toString(), new LockDeniedCommandJsonBuilderImpl());
	}

	public static CommandJsonBuilderFactoryImpl getInstance() {
		if (instance == null) {
			instance = new CommandJsonBuilderFactoryImpl();
		}
		return instance;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends Command> CommandJsonBuilder<T> getJsonBuilder(T aCommand) {
		return builders.get(aCommand.getName());
	}

}
