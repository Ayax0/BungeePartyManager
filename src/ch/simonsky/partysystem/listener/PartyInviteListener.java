package ch.simonsky.partysystem.listener;

import ch.simonsky.partysystem.events.PartyInviteEvent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PartyInviteListener implements Listener{
	
	@EventHandler
	public void onInvite(PartyInviteEvent e){
		ProxiedPlayer p = e.getPlayer();
		p.sendMessage(arg0);
	}

}
