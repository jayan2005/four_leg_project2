package activitystreamer.commands.validators.impl;

import java.util.HashMap;
import java.util.Map;

import activitystreamer.command.Command;
import activitystreamer.command.Command.Names;
import activitystreamer.commands.validators.CommandValidator;

@SuppressWarnings("rawtypes")
public class CommandValidatorFactory {

	private static CommandValidatorFactory instance;
	private Map<String, CommandValidator> validators;

	private CommandValidatorFactory() {
		validators = new HashMap<String, CommandValidator>();

		validators.put(Names.LOGIN.toString(), new LoginCommandValidator());
		validators.put(Names.REGISTER.toString(), new RegisterCommandValidator());

		validators.put(Names.ACTIVITY_MESSAGE.toString(), new ActivityMessageCommandValidator());

		validators.put(Names.AUTHENTICATE.toString(), new AuthenticateCommandValidator());
	}

	public static CommandValidatorFactory getInstance() {
		if (instance == null) {
			instance = new CommandValidatorFactory();
		}
		return instance;
	}

	@SuppressWarnings("unchecked")
	public <T extends Command> CommandValidator<T> getCommandValidator(Command aCommand) {
		return validators.get(aCommand.getName());
	}
}
