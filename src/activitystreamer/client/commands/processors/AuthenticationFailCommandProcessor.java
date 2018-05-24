package activitystreamer.client.commands.processors;

import activitystreamer.client.ClientSkeleton;
import activitystreamer.client.commands.processor.CommandProcessor;
import activitystreamer.client.ui.ClientUIManager;
import activitystreamer.commands.AuthenticationFailCommand;

public class AuthenticationFailCommandProcessor implements CommandProcessor<AuthenticationFailCommand> {

	@Override
	public void processCommand(AuthenticationFailCommand command) {
		ClientSkeleton.getInstance().stopMessageListener();
		ClientUIManager uiManager = ClientUIManager.getInstance();
		uiManager.showMessageAndCloseTextFrame(command.getInfo());
		ClientSkeleton.getInstance().closeSocket();
	}

}
