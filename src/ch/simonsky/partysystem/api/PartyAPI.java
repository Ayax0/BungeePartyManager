package ch.simonsky.partysystem.api;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import ch.simonsky.partysystem.Main;
import ch.simonsky.partysystem.enums.InviteCancelReason;
import ch.simonsky.partysystem.events.PartyInviteCloseEvent;
import ch.simonsky.partysystem.events.PartyInviteEvent;
import ch.simonsky.partysystem.events.PartyJoinEvent;
import ch.simonsky.partysystem.manager.MySQL;
import ch.simonsky.partysystem.manager.ProxyParty;
import ch.simonsky.partysystem.manager.Utils;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.scheduler.ScheduledTask;

public class PartyAPI {
	
	public static HashMap<ProxiedPlayer, ProxyParty> playerParty = new HashMap<>();
	public static HashMap<ProxiedPlayer, HashMap<ProxyParty, ScheduledTask>> partyInvite = new HashMap<>();
	
	public static boolean invite(ProxiedPlayer player, ProxiedPlayer host, ProxyParty party){
		if(canPlayerGetInvite(player)){
			if(!isPlayerInParty(player, party)){
				if(!partyInvite.containsKey(player)){partyInvite.put(player, new HashMap<>());}
				if(!partyInvite.get(player).containsKey(party)){
					Utils.callEvent(new PartyInviteEvent(player, host, party));
					partyInvite.get(player).put(party, ProxyServer.getInstance().getScheduler().schedule(Main.instance, new Runnable() {
						
						@Override
						public void run() {
							Utils.callEvent(new PartyInviteCloseEvent(player, host, party, InviteCancelReason.TIMEOUT));
							partyInvite.get(player).remove(party);
						}
					}, 10, TimeUnit.SECONDS));
					return true;
				}else{Utils.callEvent(new PartyInviteCloseEvent(player, host, party, InviteCancelReason.CANCEL));}
			}else{host.sendMessage(TextComponent.fromLegacyText(Main.prefix + ChatColor.RED + "Dieser Spieler befindet sich berreits in der Party"));}
		}else{Utils.callEvent(new PartyInviteCloseEvent(player, host, party, InviteCancelReason.DISSABLED));}
		return false;
	}
	
	public static void acceptInvite(ProxiedPlayer player, ProxiedPlayer host, ProxyParty party){
		if(partyInvite.containsKey(player)){
			if(partyInvite.get(player).containsKey(party)){
				partyInvite.get(player).get(party).cancel();
				partyInvite.get(player).remove(party);
				playerParty.put(player, party);
				party.join(player);
				Utils.callEvent(new PartyInviteCloseEvent(player, host, party, InviteCancelReason.ACCEPT));
				Utils.callEvent(new PartyJoinEvent(player, party));
				return;
			}
		}
		player.sendMessage(TextComponent.fromLegacyText(Main.prefix + ChatColor.RED + "Du hast keine Einladung von diesem Spieler"));
	}
	
	public static void declineInvite(ProxiedPlayer player, ProxiedPlayer host, ProxyParty party){
		if(partyInvite.containsKey(player)){
			if(partyInvite.get(player).containsKey(party)){
				partyInvite.get(player).get(party).cancel();
				partyInvite.get(player).remove(party);
				Utils.callEvent(new PartyInviteCloseEvent(player, host, party, InviteCancelReason.DECLINE));
				return;
			}
		}
		player.sendMessage(TextComponent.fromLegacyText(Main.prefix + ChatColor.RED + "Du hast keine Einladung von dieser Person"));
	}
	
	public static void leaveParty(ProxiedPlayer player, ProxyParty party){
		if(playerParty.containsKey(player)){
			if(playerParty.get(player) == party){
				if(party.getOwner() != player){
					playerParty.remove(player);
					party.quit(player);
					return;
				}
				player.sendMessage(TextComponent.fromLegacyText(Main.prefix + ChatColor.RED + "Du kannst als Owner die Party nicht verlassen"));
				return;
			}
		}
		player.sendMessage(TextComponent.fromLegacyText(Main.prefix + ChatColor.RED + "Du bist kein Mitglied dieser Party"));
	}
	
	public static void togglePartyRequests(ProxiedPlayer player){
		MySQL.toggleState(player);
	}
	
	public static boolean canPlayerGetInvite(ProxiedPlayer player){
		if(!MySQL.doesPlayerExists(player)){return true;}
		return MySQL.getState(player);
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
	
	public static boolean isPlayerInParty(ProxiedPlayer player, ProxyParty party){
		if(party == null){return false;}
		return party.getMember().contains(player);
	}
	
	public static boolean isPlayerInParty(ProxiedPlayer player){
		return playerParty.containsKey(player);
	}
	
	public static void sendPartyToServer(ProxyParty party, ServerInfo server){
		for(ProxiedPlayer player : party.getMember()){
			player.connect(server);
		}
	}
	
	public static void sendResourcePackToParty(ProxyParty party){
		for(ProxiedPlayer player : party.getMember()){
			//
		}
	}

}
