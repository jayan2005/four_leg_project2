package activitystreamer.server;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class UserServiceImpl implements UserService {

	private static UserServiceImpl instance;
	private Map<String, User> users;
	private UserRegistration userRegistration; 

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
	public synchronized boolean register(String username, String secret) {
		
		if (users.containsKey(username)) {
			return false;
		}

		User newUser = new User(username, secret);
		users.put(username, newUser);
		return true;
	}
	
	public synchronized UserRegistration initiateUserRegistration(String username, String secret) {
		userRegistration = new UserRegistration(username, secret);
		return userRegistration;
	}
	
	public synchronized void resetUserRegistration() {
		userRegistration = null;
	}
	
	public UserRegistration getUserRegistrationInProgress() {
		return userRegistration;
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

	@Override
	public Set<User> getAllUsers() {
		Set<User> users = new HashSet<User>(); 
		users.addAll(this.users.values());
		return users;
	}

}
