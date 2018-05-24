package activitystreamer.commands.validators.impl;

import org.apache.commons.lang3.StringUtils;

import activitystreamer.command.Command;
import activitystreamer.commands.ActivityMessageCommand;
import activitystreamer.commands.exceptions.InvalidCommandException;
import activitystreamer.commands.validators.CommandValidator;

public class ActivityMessageCommandValidator implements CommandValidator<ActivityMessageCommand> {

	@Override
	public void validateCommand(ActivityMessageCommand activityMessageCommand) throws InvalidCommandException {
		if (StringUtils.isBlank(activityMessageCommand.getUsername())) {
			throw new InvalidCommandException("Username is required.");
		}

		if (StringUtils.isBlank(activityMessageCommand.getSecret())
				&& !Command.USER_ANONYMOUS.equals(activityMessageCommand.getUsername())) {
			throw new InvalidCommandException("Secret is required.");
		}
	}

}
