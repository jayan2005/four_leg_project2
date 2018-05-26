package activitystreamer.commands;

import java.util.Set;

import activitystreamer.command.Command;
import activitystreamer.server.ServerInfo;
import activitystreamer.server.User;

public class AuthenticationSuccessCommand extends AbstractCommand {

	private String serverId;
	private Set<User> clients;
	private Set<ServerInfo> servers;

	public AuthenticationSuccessCommand(String serverId, Set<User> clients, Set<ServerInfo> servers) {
		super(Command.Names.AUTHENTICATION_SUCCESS.toString());
		this.serverId = serverId;
		this.clients = clients;
		this.servers= servers;
	}

	public Set<User> getClients() {
		return clients;
	}

	public Set<ServerInfo> getServers() {
		return servers;
	}

	public String getServerId() {
		return serverId;
	}
	
	
}
