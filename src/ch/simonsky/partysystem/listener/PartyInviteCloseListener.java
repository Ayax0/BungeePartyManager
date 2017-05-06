package ch.simonsky.partysystem.listener;

import ch.simonsky.partysystem.Main;
import ch.simonsky.partysystem.enums.InviteCancelReason;
import ch.simonsky.partysystem.events.PartyInviteCloseEvent;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PartyInviteCloseListener implements Listener{
	
	@EventHandler
	public void onInviteClose(PartyInviteCloseEvent e){
		if(e.getReason() == InviteCancelReason.ACCEPT){
			for(ProxiedPlayer player : e.getParty().getMember()){
				player.sendMessage(new TextComponent(Main.prefix + ChatColor.GRAY + "Der Spieler " + ChatColor.GOLD + e.getPlayer().getName() + ChatColor.GRAY + " ist der Party beigetreten"));
			}
		}
		if(e.getReason() == InviteCancelReason.CANCEL){
			e.getHost().sendMessage(new TextComponent(Main.prefix + ChatColor.RED + "Der Spieler " + ChatColor.GOLD + e.getPlayer().getName() + ChatColor.RED + " wurde bereits eingeladen"));
		}
		if(e.getReason() == InviteCancelReason.DECLINE){
			for(ProxiedPlayer player : e.getParty().getMember()){
				player.sendMessage(new TextComponent(Main.prefix + ChatColor.GRAY + "Der Spieler " + ChatColor.GOLD + e.getPlayer().getName() + ChatColor.GRAY + " hat die Party-Anfrage " + ChatColor.RED + "abgelehnt"));
			}
		}
		if(e.getReason() == InviteCancelReason.DISSABLED){
			e.getHost().sendMessage(new TextComponent(Main.prefix + ChatColor.RED + "Dieser Spieler hat Party-Anfragen deaktiviert"));
		}
		if(e.getReason() == InviteCancelReason.TIMEOUT){
			e.getHost().sendMessage(new TextComponent(Main.prefix + ChatColor.RED + "Die Party-Annfrage an " + ChatColor.GOLD + e.getPlayer().getName() + ChatColor.RED + " ist ausgelaufen"));
			e.getPlayer().sendMessage(new TextComponent(Main.prefix + ChatColor.RED + "Die Party-Anfrage von " + ChatColor.GOLD + e.getHost().getName() + ChatColor.RED + " ist ausgelaufen"));
		}
	}

}
