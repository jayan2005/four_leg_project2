package activitystreamer.client.commands.processors;

import activitystreamer.client.ClientSkeleton;
import activitystreamer.client.commands.processor.CommandProcessor;
import activitystreamer.client.ui.ClientUIManager;
import activitystreamer.commands.RegisterSuccessCommand;

public class RegisterSuccessCommandProcessor implements CommandProcessor<RegisterSuccessCommand> {

	@Override
	public void processCommand(RegisterSuccessCommand command) {
		ClientUIManager uiManager = ClientUIManager.getInstance();
		uiManager.showInfoDialog(uiManager.getLoginFrame(), command.getInfo(), "Register Success");
		ClientSkeleton.getInstance().sendLoginCommand();
	}

}
