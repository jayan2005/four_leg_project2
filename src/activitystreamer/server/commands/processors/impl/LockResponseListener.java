package activitystreamer.server.commands.processors.impl;

import java.util.concurrent.Callable;

import activitystreamer.server.Control;

public class LockResponseListener implements Callable<Boolean> {

	private String username;
	private int noOfServers;
	private int responseReceived;
	private boolean abort;

	public LockResponseListener(String username, int numberOf) {
		this.noOfServers = numberOf;
		this.username = username;
		Control.getInstance().registerLockResponseListener(username, this);
	}

	@Override
	public Boolean call() throws Exception {
		// listen for Lock responses
		while (!abort) {
			if (responseReceived < noOfServers) {
				continue;
			} else {
				break;
			}
		}
		Control.getInstance().unRegisterLockResponseListener(username, this);
		return responseReceived == noOfServers;
	}

	public void processResponse(boolean allowLock) {
		if (!allowLock) {
			abort = true;
		}
		responseReceived++;
	}

}
