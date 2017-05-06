package ch.simonsky.partysystem.events;

import ch.simonsky.partysystem.manager.ProxyParty;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Event;

public class PartyInviteEvent extends Event{
	
	final ProxiedPlayer player;
	final ProxiedPlayer host;
	final ProxyParty party;
	
	public PartyInviteEvent(ProxiedPlayer player, ProxiedPlayer host, ProxyParty party){
		this.player = player;
		this.host = host;
		this.party = party;
	}
	
	public ProxiedPlayer getPlayer(){
		return player;
	}
	
	public ProxiedPlayer getHost(){
		return host;
	}
	
	public ProxyParty getParty(){
		return party;
	}

}
