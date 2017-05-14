package ch.simonsky.partysystem;

import java.sql.SQLException;

import ch.simonsky.partysystem.commands.Party_CMD;
import ch.simonsky.partysystem.listener.PartyInviteCloseListener;
import ch.simonsky.partysystem.listener.PartyInviteListener;
import ch.simonsky.partysystem.listener.PartyQuitListener;
import ch.simonsky.partysystem.listener.PlayerQuitListener;
import ch.simonsky.partysystem.manager.MySQL;
import ch.simonsky.partysystem.manager.MySQLConfig;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;

public class Main extends Plugin{
	
	public static String prefix = ChatColor.GRAY + "[" + ChatColor.DARK_PURPLE + "Party" + ChatColor.GRAY + "] ";
	public static boolean mysql;
	public static Plugin instance;
	
	@Override
	public void onEnable() {
		instance = this;
		
		MySQLConfig.createIfNotExists();
		try{
			MySQL.connect(MySQLConfig.getHost(), MySQLConfig.getPort(), MySQLConfig.getDatabase(), MySQLConfig.getUsername(), MySQLConfig.getPasswort());
			MySQL.createTable();
			mysql = true;
		}catch(SQLException e){
			ProxyServer.getInstance().getConsole().sendMessage(TextComponent.fromLegacyText(prefix + ChatColor.RED + "Es konnte keine MySQL verbindung aufgebaut werden!"));
			ProxyServer.getInstance().getConsole().sendMessage(TextComponent.fromLegacyText(prefix + ChatColor.RED + "Bitte überprüffe deine eingetragenen Werte bei /plugins/BungeePartyManager/config.yml"));
			mysql = false;
		}
		
		BungeeCord.getInstance().registerChannel("Resourcepack");
		
		PluginManager pm = ProxyServer.getInstance().getPluginManager();
		pm.registerCommand(this, new Party_CMD("party"));
		pm.registerCommand(this, new Party_CMD("p"));
		
		pm.registerListener(this, new PartyInviteCloseListener());
		pm.registerListener(this, new PartyInviteListener());
		pm.registerListener(this, new PartyQuitListener());
		pm.registerListener(this, new PlayerQuitListener());
	}

}
