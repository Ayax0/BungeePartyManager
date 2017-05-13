package ch.simonsky.partysystem.listener;

import ch.simonsky.partysystem.api.PartyAPI;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PlayerQuitListener implements Listener{
	
	@EventHandler
	public void onQuit(PlayerDisconnectEvent e){
		if(PartyAPI.isPlayerInParty(e.getPlayer()) && PartyAPI.getPlayerParty(e.getPlayer()).getOwner() == e.getPlayer()){
			PartyAPI.disbandParty(PartyAPI.getPlayerParty(e.getPlayer()));
		}
	}

}
