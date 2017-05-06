package ch.simonsky.partysystem.listener;

import ch.simonsky.partysystem.Main;
import ch.simonsky.partysystem.events.PartyInviteEvent;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PartyInviteListener implements Listener{
	
	@EventHandler
	public void onInvite(PartyInviteEvent e){
		ProxiedPlayer p = e.getPlayer();
		
		TextComponent prefix = new TextComponent(Main.prefix + ChatColor.GRAY + "Du hast eine Party anfrage von " + ChatColor.GOLD + e.getParty().getOwner() + ChatColor.GRAY + " bekommen");
		TextComponent prefix2 = new TextComponent(Main.prefix + ChatColor.GRAY + "Anfrage ");
		TextComponent mid = new TextComponent(ChatColor.GRAY + " oder ");
		
		TextComponent message = new TextComponent(ChatColor.GREEN + "Annehmen");
		message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/p accept " + e.getParty().getOwner().getName()));
		message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GREEN + "Anfrage Annehmen").create()));
		
		TextComponent message2 = new TextComponent(ChatColor.RED + "Ablehnen");
		message2.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/p decline " + e.getParty().getOwner().getName()));
		message2.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.RED + "Anfrage Ablehnen").create()));
		
		prefix2.addExtra(message);
		prefix2.addExtra(mid);
		prefix2.addExtra(message2);
		
		p.sendMessage(prefix);
		p.sendMessage(prefix2);
	}

}
