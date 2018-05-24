package activitystreamer.commands.validators.impl;

import org.apache.commons.lang3.StringUtils;

import activitystreamer.commands.AuthenticateCommand;
import activitystreamer.commands.exceptions.InvalidCommandException;
import activitystreamer.commands.validators.CommandValidator;

public class AuthenticateCommandValidator implements CommandValidator<AuthenticateCommand> {

	@Override
	public void validateCommand(AuthenticateCommand authenticateCommand) throws InvalidCommandException {
		if (StringUtils.isBlank(authenticateCommand.getSecret())) {
			throw new InvalidCommandException("Secret is required.");
		}
	}

}
