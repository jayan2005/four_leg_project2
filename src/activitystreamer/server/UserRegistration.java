package activitystreamer.server;

public class UserRegistration {
	
	private String userName;
	private String secret;
	private long requestTime;
	
	public UserRegistration(String userName, String secret) {
		this.userName = userName;
		this.secret=secret;
		this.requestTime=System.nanoTime();
	}
	public String getUserName() {
		return userName;
	}
	public String getSecret() {
		return secret;
	}
	public long getRequestTime() {
		return requestTime;
	}

}
