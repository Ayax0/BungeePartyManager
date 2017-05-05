package ch.simonsky.partysystem;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.plugin.Plugin;

public class Main extends Plugin{
	
	public static String prefix = ChatColor.GRAY + "[" + ChatColor.DARK_PURPLE + "Party" + ChatColor.GRAY + "] ";
	public static Plugin instance;
	
	@Override
	public void onEnable() {
		instance = this;
		
	}

}
