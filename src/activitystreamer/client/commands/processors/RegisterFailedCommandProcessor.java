package activitystreamer.client.commands.processors;

import activitystreamer.client.ClientSkeleton;
import activitystreamer.client.commands.processor.CommandProcessor;
import activitystreamer.client.ui.ClientUIManager;
import activitystreamer.commands.RegisterFailedCommand;

public class RegisterFailedCommandProcessor implements CommandProcessor<RegisterFailedCommand> {

	@Override
	public void processCommand(RegisterFailedCommand command) {
		ClientSkeleton.getInstance().stopMessageListener();
		ClientUIManager uiManager = ClientUIManager.getInstance();
		uiManager.showErrorDialog(uiManager.getLoginFrame(), command.getInfo(), "Register Failed");
		ClientSkeleton.getInstance().closeSocket();
	}

}
