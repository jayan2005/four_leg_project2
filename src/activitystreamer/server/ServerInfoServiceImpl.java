package activitystreamer.server;

import java.util.HashSet;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerInfoServiceImpl implements ServerInfoService {

	private static final Logger log = LogManager.getLogger();
	private static ServerInfoServiceImpl instance;
	private static Set<ServerInfo> servers;

	private ServerInfoServiceImpl() {
		servers = new HashSet<ServerInfo>();
	}

	public static ServerInfoServiceImpl getInstance() {
		if (instance == null) {
			instance = new ServerInfoServiceImpl();
		}
		return instance;
	}

	@Override
	public void udpateServerLoad(String id, int load) {
		ServerInfo serverInfo = getServerInfo(id);
		if (serverInfo != null) {
			serverInfo.setLoad(load);
		}
	}

	@Override
	public ServerInfo getServerInfo(String id) {
		for (ServerInfo serverInfo : servers) {
			if (serverInfo.getId().equals(id)) {
				return serverInfo;
			}
		}
		return null;
	}

	@Override
	public ServerInfo addServerInfo(String id, String secret, String hostname, int port, int load) {
		ServerInfo serverInfo = new ServerInfo();
		serverInfo.setId(id);
		serverInfo.setLoad(load);
		serverInfo.setHostname(hostname);
		serverInfo.setPort(port);
		serverInfo.setSecret(secret);

		servers.add(serverInfo);
		return serverInfo;
	}
	
	@Override
	public void addServerInfo(ServerInfo aServerInfo) {
		servers.add(aServerInfo);
	}

	@Override
	public Set<ServerInfo> getAllServersInfo() {
		return servers;
	}
	
	// Removing shutting down server from the server list
	public boolean removeServer(String id) {
		
		log.info("removing the server shutting down from the list");
		log.info("Server ID of the command : " + id);

		ServerInfo shuttingDownServer = getServerInfo(id);
		
		if(servers !=null && servers.contains(shuttingDownServer.getId())) {
			servers.remove(shuttingDownServer.getId());
			log.info("Server ID " + shuttingDownServer.getId() + " removed from the list");
			return true;
		}
		return false;
		
	}
}
