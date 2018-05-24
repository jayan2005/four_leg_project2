package activitystreamer.client.ui;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.json.simple.JSONObject;

public class ClientUIManager {

	private static ClientUIManager instance;

	private TextFrame textFrame;
	private LoginFrame loginFrame;

	private ClientUIManager() {

	}

	public static ClientUIManager getInstance() {
		if (instance == null) {
			instance = new ClientUIManager();
		}
		return instance;
	}

	public void showErrorDialog(String message, String title) {
		JOptionPane.showMessageDialog(loginFrame != null ? loginFrame : textFrame, message, title,
				JOptionPane.ERROR_MESSAGE);
	}

	public void showErrorDialog(JFrame frame, String message, String title) {
		JOptionPane.showMessageDialog(frame, message, title, JOptionPane.ERROR_MESSAGE);
	}

	public void showInfoDialog(JFrame frame, String message, String title) {
		JOptionPane.showMessageDialog(frame, message, title, JOptionPane.INFORMATION_MESSAGE);
	}

	public void showDialog(JFrame frame, String message, String title, int messageType) {
		JOptionPane.showMessageDialog(frame, message, title, messageType);
	}

	public void showLoginFrame() {
		if (loginFrame == null) {
			loginFrame = new LoginFrame();
		}
		loginFrame.setVisible(true);
	}

	public void showTextFrame() {
		if (textFrame == null) {
			textFrame = new TextFrame();
		}
		textFrame.setVisible(true);
	}

	public void closeLoginFrame() {
		if (loginFrame != null) {
			loginFrame.dispose();
			loginFrame.setVisible(false);
			loginFrame = null;
		}
	}

	public void closeTextFrame() {
		if (textFrame != null) {
			textFrame.dispose();
			textFrame.setVisible(false);
			textFrame = null;
			showLoginFrame();
		}
	}

	public void showMessageAndCloseTextFrame(String message) {
		if (textFrame != null) {
			showErrorDialog(textFrame, message, "Error");
			textFrame.setVisible(false);
			textFrame = null;
			showLoginFrame();
		}
	}

	public void handleLoginSuccess() {
		closeLoginFrame();
		showTextFrame();
		textFrame.setConnectionInfo();
	}

	public void handleLogout() {
		closeTextFrame();
		showLoginFrame();
	}

	public void handleIOError() {
		closeTextFrame();
		showLoginFrame();
	}

	public LoginFrame getLoginFrame() {
		return loginFrame;
	}

	public void updateTextFrame(JSONObject response) {
		textFrame.setOutputText(response);
	}

}
