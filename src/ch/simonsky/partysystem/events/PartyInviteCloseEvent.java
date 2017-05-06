package ch.simonsky.partysystem.events;

import ch.simonsky.partysystem.enums.InviteCancelReason;
import ch.simonsky.partysystem.manager.ProxyParty;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Event;

public class PartyInviteCloseEvent extends Event{
	
	final ProxiedPlayer player;
	final ProxiedPlayer host;
	final ProxyParty party;
	final InviteCancelReason reason;
	
	public PartyInviteCloseEvent(ProxiedPlayer player, ProxiedPlayer host, ProxyParty party, InviteCancelReason reason){
		this.player = player;
		this.host = host;
		this.party = party;
		this.reason = reason;
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
	
	public InviteCancelReason getReason(){
		return reason;
	}

}
