package activitystreamer.server;

public class ServerInfo {

	private String id;
	private String hostname;
	private int port;
	private int load;
	private String secret;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public int getLoad() {
		return load;
	}

	public void setLoad(int load) {
		this.load = load;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}
	
	@Override
	public int hashCode() {
		return 31*id.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ServerInfo) {
			ServerInfo newServer = (ServerInfo)obj;
			return id.equals(newServer.getId());
		}
		return false;
	}
	

}
