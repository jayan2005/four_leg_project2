package activitystreamer.client.commands.processors;

import activitystreamer.client.ClientSkeleton;
import activitystreamer.client.commands.processor.CommandProcessor;
import activitystreamer.client.ui.ClientUIManager;
import activitystreamer.commands.LoginFailedCommand;

public class LoginFailedCommandProcessor implements CommandProcessor<LoginFailedCommand> {

	@Override
	public void processCommand(LoginFailedCommand command) {
		ClientSkeleton.getInstance().stopMessageListener();
		ClientUIManager uiManager = ClientUIManager.getInstance();
		uiManager.showErrorDialog(uiManager.getLoginFrame(), command.getInfo(), "Login Failed");
		ClientSkeleton.getInstance().closeSocket();
	}

}
