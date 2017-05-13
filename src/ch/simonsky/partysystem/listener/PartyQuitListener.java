package ch.simonsky.partysystem.listener;

import ch.simonsky.partysystem.Main;
import ch.simonsky.partysystem.api.PartyAPI;
import ch.simonsky.partysystem.enums.QuitReason;
import ch.simonsky.partysystem.events.PartyQuitEvent;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PartyQuitListener implements Listener{
	
	@EventHandler
	public void onPartyQuit(PartyQuitEvent e){
		if(e.getReason() == QuitReason.CLOSE_QUIT){
			e.getPlayer().sendMessage(TextComponent.fromLegacyText(Main.prefix + ChatColor.RED + "Die Party wurde aufgelösst"));
		}
		if(e.getReason() == QuitReason.NORMAL_QUIT){
			e.getPlayer().sendMessage(TextComponent.fromLegacyText(Main.prefix + ChatColor.RED + "Du hast die Party verlassen"));
			if(!e.getParty().getMember().isEmpty()){
				for(ProxiedPlayer member : e.getParty().getMember()){
					if(member != e.getPlayer()){
						member.sendMessage(TextComponent.fromLegacyText(Main.prefix + ChatColor.RED + "Der Spieler " + ChatColor.GOLD + e.getPlayer().getName() + ChatColor.RED + " hat die Party verlassen"));
					}
				}
			}
		}
		
		PartyAPI.playerParty.remove(e.getPlayer());
	}

}
