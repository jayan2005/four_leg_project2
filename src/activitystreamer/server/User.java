package activitystreamer.server;

public class User {

	private String username;
	private String secret;

	public User(String username, String secret) {
		this.username = username;
		this.secret = secret;
	}

	public String getUsername() {
		return username;
	}

	public String getSecret() {
		return secret;
	}
	
	@Override
	public int hashCode() {
		return 31*username.hashCode()*secret.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof User) {
			User newUser = (User)obj;
			return username.equals(newUser.getUsername());
		}
		return false;
	}
	
}
