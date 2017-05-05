package ch.simonsky.partysystem.events;

import ch.simonsky.partysystem.enums.QuitReason;
import ch.simonsky.partysystem.manager.ProxyParty;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Event;

public class PartyQuitEvent extends Event{
	
	final ProxiedPlayer player;
	final ProxyParty party;
	final QuitReason reason;
	
	public PartyQuitEvent(ProxiedPlayer player, ProxyParty party, QuitReason reason){
		this.player = player;
		this.party = party;
		this.reason = reason;
	}
	
	public ProxiedPlayer getPlayer(){
		return player;
	}
	
	public ProxyParty getParty(){
		return party;
	}
	
	public QuitReason getReason(){
		return reason;
	}

}
