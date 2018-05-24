package activitystreamer.server;

public interface UserService {

	User getUser(String username);

	boolean register(String username, String secret);

	boolean unregister(String username, String secret);

	boolean login(String username, String secret);

}
