package ch.simonsky.partysystem.api;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import ch.simonsky.partysystem.Main;
import ch.simonsky.partysystem.enums.InviteCancelReason;
import ch.simonsky.partysystem.enums.QuitReason;
import ch.simonsky.partysystem.events.PartyInviteCloseEvent;
import ch.simonsky.partysystem.events.PartyInviteEvent;
import ch.simonsky.partysystem.events.PartyJoinEvent;
import ch.simonsky.partysystem.events.PartyQuitEvent;
import ch.simonsky.partysystem.manager.ProxyParty;
import ch.simonsky.partysystem.manager.Utils;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.scheduler.ScheduledTask;

public class PartyAPI {
	
	private static HashMap<ProxiedPlayer, ProxyParty> playerParty = new HashMap<>();
	private static HashMap<ProxiedPlayer, HashMap<ProxyParty, ScheduledTask>> partyInvite = new HashMap<>();
	
	public static boolean invite(ProxiedPlayer player, ProxyParty party){
		Utils.callEvent(new PartyInviteEvent(player, party));
		if(!partyInvite.containsKey(player)){partyInvite.put(player, new HashMap<>());}
		if(!partyInvite.get(player).containsKey(party)){
			partyInvite.get(player).put(party, ProxyServer.getInstance().getScheduler().schedule(Main.instance, new Runnable() {
				
				@Override
				public void run() {
					Utils.callEvent(new PartyInviteCloseEvent(player, InviteCancelReason.TIMEOUT));
					partyInvite.get(player).remove(party);
				}
			}, 10, TimeUnit.SECONDS));
			return true;
		}
		return false;
	}
	
	public static void acceptInvite(ProxiedPlayer player, ProxyParty party){
		if(partyInvite.containsKey(player)){
			if(partyInvite.get(player).containsKey(party)){
				partyInvite.get(player).get(party).cancel();
				playerParty.put(player, party);
				party.join(player);
				Utils.callEvent(new PartyInviteCloseEvent(player, InviteCancelReason.ACCEPT));
				Utils.callEvent(new PartyJoinEvent(player, party));
				return;
			}
		}
		player.sendMessage(new TextComponent(Main.prefix + ChatColor.RED + "Du hast keine Einladung in diese Party"));
	}
	
	public static void declineInvite(ProxiedPlayer player, ProxyParty party){
		if(partyInvite.containsKey(player)){
			if(partyInvite.get(player).containsKey(party)){
				partyInvite.get(player).get(party).cancel();
				Utils.callEvent(new PartyInviteCloseEvent(player, InviteCancelReason.DECLINE));
				return;
			}
		}
		player.sendMessage(new TextComponent(Main.prefix + ChatColor.RED + "Du hast keine Einladung in diese Party"));
	}
	
	public static void leaveParty(ProxiedPlayer player, ProxyParty party){
		if(playerParty.containsKey(player)){
			if(playerParty.get(player) == party){
				if(party.getOwner() != player){
					playerParty.remove(player);
					party.quit(player);
					Utils.callEvent(new PartyQuitEvent(player, party, QuitReason.NORMAL_QUIT));
					return;
				}
				player.sendMessage(new TextComponent(Main.prefix + ChatColor.RED + "Du kannst als Owner die Party nicht verlassen"));
				return;
			}
		}
		player.sendMessage(new TextComponent(Main.prefix + ChatColor.RED + "Du bist kein Mitglied dieser Party"));
	}
	
	public static void togglePartyRequests(ProxiedPlayer player){
		//MySQL
	}
	
	public static ProxiedPlayer getOwner(ProxyParty party){
		return party.getOwner();
	}
	
	public static List<ProxiedPlayer> getMember(ProxyParty party){
		return party.getMember();
	}
	
	public static void disbandParty(ProxyParty party){
		party.close();
	}
	
	public static ProxyParty getPlayerParty(ProxiedPlayer player){
		return playerParty.get(player);
	}
	
	public static void sendPartyToServer(ProxyParty party, ServerInfo server){
		for(ProxiedPlayer player : party.getMember()){
			player.connect(server);
		}
	}
	
	public static void sendResourcePackToParty(ProxyParty party, String resource){
		for(ProxiedPlayer player : party.getMember()){
			player.sendData("MC|Rpack", resource.getBytes());
		}
	}

}
