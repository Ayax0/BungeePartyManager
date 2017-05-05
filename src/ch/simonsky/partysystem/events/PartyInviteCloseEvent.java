package ch.simonsky.partysystem.events;

import ch.simonsky.partysystem.enums.InviteCancelReason;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Event;

public class PartyInviteCloseEvent extends Event{
	
	final ProxiedPlayer player;
	final InviteCancelReason reason;
	
	public PartyInviteCloseEvent(ProxiedPlayer player, InviteCancelReason reason){
		this.player = player;
		this.reason = reason;
	}
	
	public ProxiedPlayer getPlayer(){
		return player;
	}
	
	public InviteCancelReason getReason(){
		return reason;
	}

}
