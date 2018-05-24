package activitystreamer.server;

import java.util.ArrayList;
import java.util.List;

public class ServerInfoServiceImpl implements ServerInfoService {

	private static ServerInfoServiceImpl instance;
	private static ArrayList<ServerInfo> servers;

	private ServerInfoServiceImpl() {
		servers = new ArrayList<ServerInfo>();
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
	public ServerInfo addServerInfo(String id, String hostname, int port, int load) {
		ServerInfo serverInfo = new ServerInfo();
		serverInfo.setId(id);
		serverInfo.setLoad(load);
		serverInfo.setHostname(hostname);
		serverInfo.setPort(port);

		servers.add(serverInfo);
		return serverInfo;
	}

	@Override
	public List<ServerInfo> getAllServersInfo() {
		return servers;
	}
}
