package activitystreamer.commands.builders.impl;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;

import activitystreamer.command.Command;
import activitystreamer.command.Command.Names;
import activitystreamer.commands.builder.CommandBuilder;
import activitystreamer.commands.builder.CommandBuilderFactory;

@SuppressWarnings("rawtypes")
public class CommandBuilderFactoryImpl implements CommandBuilderFactory {

	private static CommandBuilderFactoryImpl instance;

	private Map<String, CommandBuilder> builders;

	private CommandBuilderFactoryImpl() {
		builders = new HashMap<String, CommandBuilder>();
		builders.put(Command.Names.REGISTER.toString(), new RegisterCommandBuilderImpl());
		builders.put(Command.Names.REGISTER_SUCCESS.toString(), new RegisterSuccessCommandBuilderImpl());
		builders.put(Command.Names.REGISTER_FAILED.toString(), new RegisterFailedCommandBuilderImpl());

		builders.put(Command.Names.LOGIN.toString(), new LoginCommandBuilderImpl());
		builders.put(Command.Names.LOGIN_SUCCESS.toString(), new LoginSuccessCommandBuilderImpl());
		builders.put(Command.Names.LOGIN_FAILED.toString(), new LoginFailedCommandBuilderImpl());

		builders.put(Command.Names.LOGOUT.toString(), new LogoutCommandBuilderImpl());

		builders.put(Command.Names.REDIRECT.toString(), new RedirectCommandBuilderImpl());

		builders.put(Command.Names.ACTIVITY_MESSAGE.toString(), new ActivityMessageCommandBuilderImpl());
		builders.put(Command.Names.ACTIVITY_BROADCAST.toString(), new ActivityBroadcastCommandBuilderImpl());

		builders.put(Command.Names.AUTHENTICATE.toString(), new AuthenticateCommandBuilderImpl());
		builders.put(Command.Names.AUTHENTICATION_SUCCESS.toString(), new AuthenticationSuccessCommandBuilderImpl());
		builders.put(Command.Names.AUTHENTICATION_FAIL.toString(), new AuthenticationFailCommandBuilderImpl());

		builders.put(Command.Names.INVALID_MESSAGE.toString(), new InvalidMessageCommandBuilderImpl());
		builders.put(Command.Names.SERVER_ANNOUNCE.toString(), new ServerAnnounceCommandBuilderImpl());
		builders.put(Command.Names.SERVER_QUIT.toString(), new ServerQuitCommandBuilderImpl());

		builders.put(Command.Names.LOCK_REQUEST.toString(), new LockRequestCommandBuilderImpl());
		builders.put(Command.Names.LOCK_ALLOWED.toString(), new LockAllowedCommandBuilderImpl());
		builders.put(Command.Names.LOCK_DENIED.toString(), new LockDeniedCommandBuilderImpl());
	}

	public static CommandBuilderFactoryImpl getInstance() {
		if (instance == null) {
			instance = new CommandBuilderFactoryImpl();
		}
		return instance;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends Command> CommandBuilder<T> getCommandBuilder(JSONObject jsonObject) {
		String commandName = (String) jsonObject.get("command");
		if (commandName == null) {
			return null;
		}
		Enum command = Names.valueOf(Names.class, commandName);
		if (command == null) {
			return null;
		}
		return builders.get(command.toString());
	}

}
