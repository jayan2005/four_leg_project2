package activitystreamer.client.commands.processors;

import activitystreamer.client.ClientSkeleton;
import activitystreamer.client.commands.processor.CommandProcessor;
import activitystreamer.client.ui.ClientUIManager;
import activitystreamer.commands.InvalidMessageCommand;

public class InvalidMessageCommandProcessor implements CommandProcessor<InvalidMessageCommand> {

	@Override
	public void processCommand(InvalidMessageCommand command) {
		ClientSkeleton.getInstance().stopMessageListener();
		ClientUIManager uiManager = ClientUIManager.getInstance();
		uiManager.showMessageAndCloseTextFrame(command.getInfo());
		ClientSkeleton.getInstance().closeSocket();
	}

}
