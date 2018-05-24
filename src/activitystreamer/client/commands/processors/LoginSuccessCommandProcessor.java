package activitystreamer.client.commands.processors;

import activitystreamer.client.commands.processor.CommandProcessor;
import activitystreamer.client.ui.ClientUIManager;
import activitystreamer.commands.LoginSuccessCommand;
import activitystreamer.util.Settings;

public class LoginSuccessCommandProcessor implements CommandProcessor<LoginSuccessCommand> {

	@Override
	public void processCommand(LoginSuccessCommand loginSuccessCommand) {
		ClientUIManager uiManager = ClientUIManager.getInstance();
		uiManager.handleLoginSuccess();
		uiManager.showInfoDialog(uiManager.getLoginFrame(), loginSuccessCommand.getInfo(),
				Settings.isConnectionRedirect() ? "Redirect Login Success" : "Login Success");
	}

}
