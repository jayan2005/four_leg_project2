package activitystreamer.client.commands.processors;

import activitystreamer.client.commands.processor.CommandProcessor;
import activitystreamer.client.ui.ClientUIManager;
import activitystreamer.commands.ActivityBroadcastCommand;

public class ActivityBroadcastCommandProcessor implements CommandProcessor<ActivityBroadcastCommand> {

	@Override
	public void processCommand(ActivityBroadcastCommand command) {
		ClientUIManager uiManager = ClientUIManager.getInstance();
		uiManager.updateTextFrame(command.getActivity());
	}

}
