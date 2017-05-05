package ch.simonsky.partysystem.events;

import ch.simonsky.partysystem.manager.ProxyParty;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Event;

public class PartyJoinEvent extends Event{
	
	final ProxiedPlayer player;
	final ProxyParty party;
	
	public PartyJoinEvent(ProxiedPlayer player, ProxyParty party){
		this.player = player;
		this.party = party;
	}
	
	public ProxiedPlayer getPlayer(){
		return player;
	}
	
	public ProxyParty getParty(){
		return party;
	}

}
