package activitystreamer.server;

import java.util.List;

public interface ServerInfoService {

	List<ServerInfo> getAllServersInfo();

	ServerInfo getServerInfo(String id);

	void udpateServerLoad(String id, int load);

	ServerInfo addServerInfo(String id, String hostname, int port, int load);

}
