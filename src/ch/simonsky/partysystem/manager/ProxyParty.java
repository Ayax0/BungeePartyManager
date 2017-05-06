package ch.simonsky.partysystem.manager;

import java.util.ArrayList;
import java.util.List;

import ch.simonsky.partysystem.Main;
import ch.simonsky.partysystem.enums.QuitReason;
import ch.simonsky.partysystem.events.PartyCloseEvent;
import ch.simonsky.partysystem.events.PartyJoinEvent;
import ch.simonsky.partysystem.events.PartyQuitEvent;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class ProxyParty {
	
	ProxiedPlayer owner;
	List<ProxiedPlayer> member = new ArrayList<>();
	
	public ProxyParty(ProxiedPlayer owner){
		this.owner = owner;
		this.member.add(owner);
	}
	
	public void join(ProxiedPlayer player){
		if(!member.contains(player)){
			member.add(player);
			Utils.callEvent(new PartyJoinEvent(player, this));
			return;
		}
		player.sendMessage(TextComponent.fromLegacyText(Main.prefix + ChatColor.RED + "Du bist der Party bereits beigetreten"));
	}
	
	public void quit(ProxiedPlayer player){
		if(!member.isEmpty()){
			if(member.contains(player)){
				Utils.callEvent(new PartyQuitEvent(player, this, QuitReason.NORMAL_QUIT));
				return;
			}
		}
		player.sendMessage(TextComponent.fromLegacyText(Main.prefix + ChatColor.RED + "Es ist ein Fehler aufgetreten"));
	}
	
	public void close(){
		if(!member.isEmpty()){
			for(ProxiedPlayer player : member){
				Utils.callEvent(new PartyQuitEvent(player, this, QuitReason.CLOSE_QUIT));
			}
		}
		Utils.callEvent(new PartyCloseEvent(this));
	}
	
	public ProxiedPlayer getOwner(){
		return owner;
	}
	
	public List<ProxiedPlayer> getMember(){
		return member;
	}

}
