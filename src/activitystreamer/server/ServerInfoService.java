package activitystreamer.server;

import java.util.Set;

public interface ServerInfoService {

	Set<ServerInfo> getAllServersInfo();

	ServerInfo getServerInfo(String id);

	void udpateServerLoad(String id, int load);

	ServerInfo addServerInfo(String id, String hostname, String secret, int port, int load);
	
	void addServerInfo(ServerInfo aServerInfo);

}
