package activitystreamer.server;

import java.util.HashMap;
import java.util.Map;

public class UserServiceImpl implements UserService {

	private static UserServiceImpl instance;
	private Map<String, User> users;

	private UserServiceImpl() {
		users = new HashMap<String, User>();
	}

	public static UserServiceImpl getInstance() {
		if (instance == null) {
			instance = new UserServiceImpl();
		}
		return instance;
	}

	@Override
	public boolean register(String username, String secret) {
		if (users.containsKey(username)) {
			return false;
		}

		User newUser = new User(username, secret);
		users.put(username, newUser);
		return true;
	}

	@Override
	public boolean unregister(String username, String secret) {
		if (users.containsKey(username)) {
			users.remove(username);
		}
		return true;
	}

	@Override
	public boolean login(String username, String secret) {
		if (!users.containsKey(username)) {
			return false;
		}

		User user = users.get(username);
		return user.getSecret().equals(secret);
	}

	@Override
	public User getUser(String username) {
		return users.get(username);
	}

}