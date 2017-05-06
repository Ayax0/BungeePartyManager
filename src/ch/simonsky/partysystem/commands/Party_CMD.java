package ch.simonsky.partysystem.commands;

import ch.simonsky.partysystem.Main;
import ch.simonsky.partysystem.api.PartyAPI;
import ch.simonsky.partysystem.manager.ProxyParty;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class Party_CMD extends Command{

	public Party_CMD(String name) {
		super(name);
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if(sender instanceof ProxiedPlayer){
			ProxiedPlayer p = (ProxiedPlayer)sender;
			if(args.length > 0){
				//party invite [Player]
				if(args[0].equalsIgnoreCase("invite")){
					if(args.length > 1){
						if(ProxyServer.getInstance().getPlayer(args[1]) != null){
							ProxiedPlayer member = ProxyServer.getInstance().getPlayer(args[1]);
							if(!PartyAPI.isPlayerInParty(p)){
								if(PartyAPI.canPlayerGetInvite(member)){
									PartyAPI.playerParty.put(p, new ProxyParty(p));
								
									ProxyParty party = PartyAPI.getPlayerParty(p);
									PartyAPI.invite(member, p, party);
									p.sendMessage(new TextComponent(Main.prefix + ChatColor.GRAY + "Du hast eine Party-Anfrage an " + ChatColor.GOLD + member.getName() + ChatColor.GRAY + " versendet"));
								}else{p.sendMessage(new TextComponent(Main.prefix + ChatColor.RED + "Dieser Spieler hat Party-Anfragen deaktiviert"));}
							}else{p.sendMessage(new TextComponent(Main.prefix + ChatColor.RED + "Du befindest dich bereits in einer Party"));}
						}else{p.sendMessage(new TextComponent(Main.prefix + ChatColor.RED + "Der Spieler " + ChatColor.GOLD + args[1] + ChatColor.RED + " ist nicht online"));}
					}else{p.sendMessage(new TextComponent(Main.prefix + ChatColor.RED + "/party invite [Player]"));}
				}
				
				//party accept [Player]
				if(args[0].equalsIgnoreCase("accept")){
					if(args.length > 1){
						if(ProxyServer.getInstance().getPlayer(args[1]) != null){
							ProxiedPlayer owner = ProxyServer.getInstance().getPlayer(args[1]);
							if((PartyAPI.isPlayerInParty(owner) && PartyAPI.getPlayerParty(owner).getOwner() == owner) || !PartyAPI.isPlayerInParty(owner)){
								PartyAPI.acceptInvite(p, owner, PartyAPI.getPlayerParty(owner));
							}else{p.sendMessage(new TextComponent(Main.prefix + ChatColor.RED + "Dieser Spieler ist nicht Owner dieser Party"));}
						}else{p.sendMessage(new TextComponent(Main.prefix + ChatColor.RED + "Der Spieler " + ChatColor.GOLD + args[1] + ChatColor.RED + " ist nicht online"));}
					}else{p.sendMessage(new TextComponent(Main.prefix + ChatColor.RED + "/party accept [Player]"));}
				}
				
				//party decline [Player]
				if(args[0].equalsIgnoreCase("decline")){
					if(args.length > 1){
						if(ProxyServer.getInstance().getPlayer(args[1]) != null){
							ProxiedPlayer owner = ProxyServer.getInstance().getPlayer(args[1]);
							if((PartyAPI.isPlayerInParty(owner) && PartyAPI.getPlayerParty(owner).getOwner() == owner) || !PartyAPI.isPlayerInParty(owner)){
								PartyAPI.declineInvite(p, owner, PartyAPI.getPlayerParty(owner));
							}else{p.sendMessage(new TextComponent(Main.prefix + ChatColor.RED + "Dieser Spieler ist nicht Owner dieser Party"));}
						}else{p.sendMessage(new TextComponent(Main.prefix + ChatColor.RED + "Der Spieler " + ChatColor.GOLD + args[1] + ChatColor.RED + " ist nicht online"));}
					}else{p.sendMessage(new TextComponent(Main.prefix + ChatColor.RED + "/party decline [Player]"));}
				}
				
				//party leave
				if(args[0].equalsIgnoreCase("leave")){
					if(PartyAPI.isPlayerInParty(p)){
						if(PartyAPI.getPlayerParty(p).getOwner() != p){
							PartyAPI.leaveParty(p, PartyAPI.getPlayerParty(p));
						}else{
							p.sendMessage(new TextComponent(Main.prefix + ChatColor.RED + "Du kannst als Owner die Party nicht verlassen"));
							p.sendMessage(new TextComponent(Main.prefix + ChatColor.RED + "Benutze " + ChatColor.GOLD + "/party disband " + ChatColor.RED + "um die Party aufzulösen"));
						}
					}else{p.sendMessage(new TextComponent(Main.prefix + ChatColor.RED + "Du befindest dich in keiner Party"));}
				}
				
				//party owner
				if(args[0].equalsIgnoreCase("owner")){
					if(PartyAPI.isPlayerInParty(p)){
						ProxiedPlayer owner = PartyAPI.getPlayerParty(p).getOwner();
						p.sendMessage(new TextComponent(Main.prefix + ChatColor.GRAY + "Der Spieler " + ChatColor.GOLD + owner.getName() + ChatColor.GRAY + " ist der Owner dieser Party"));
					}else{p.sendMessage(new TextComponent(Main.prefix + ChatColor.RED + "Du befindest dich in keiner Party"));}
				}
				
				//party disband
				if(args[0].equalsIgnoreCase("disband")){
					if(PartyAPI.isPlayerInParty(p)){
						if(PartyAPI.getPlayerParty(p).getOwner() == p){
							PartyAPI.disbandParty(PartyAPI.getPlayerParty(p));
						}else{p.sendMessage(new TextComponent(Main.prefix + ChatColor.RED + "Die Party kan nur vom Owner aufgelösst werden"));}
					}else{p.sendMessage(new TextComponent(Main.prefix + ChatColor.RED + "Du befindest dich in keiner Party"));}
				}
				
				//party toggle
				if(args[0].equalsIgnoreCase("toggle")){
					PartyAPI.togglePartyRequests(p);
					if(PartyAPI.canPlayerGetInvite(p)){
						p.sendMessage(new TextComponent(Main.prefix + ChatColor.GREEN + "Spieler können dich nun in eine Party einladen"));
					}else{p.sendMessage(new TextComponent(Main.prefix + ChatColor.RED + "Spieler können dich nun nicht mehr in eine Party einladen"));}
				}
			}else{
				p.sendMessage(new TextComponent(Main.prefix + ChatColor.DARK_PURPLE + "-< " + ChatColor.GOLD + "Party Commands" + ChatColor.DARK_PURPLE + " >-"));
				p.sendMessage(new TextComponent(Main.prefix + ChatColor.RED + "/party invite [Player]"));
				p.sendMessage(new TextComponent(Main.prefix + ChatColor.RED + "/party accept [Player]"));
				p.sendMessage(new TextComponent(Main.prefix + ChatColor.RED + "/party decline [Player]"));
				p.sendMessage(new TextComponent(Main.prefix + ChatColor.RED + "/party leave"));
				p.sendMessage(new TextComponent(Main.prefix + ChatColor.RED + "/party owner"));
				p.sendMessage(new TextComponent(Main.prefix + ChatColor.RED + "/party disband"));
				p.sendMessage(new TextComponent(Main.prefix + ChatColor.RED + "/party toggle"));
			}
		}
	}

}
