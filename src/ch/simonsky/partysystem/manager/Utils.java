package ch.simonsky.partysystem.manager;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Event;

public class Utils {
	
	public static void callEvent(Event event){
		ProxyServer.getInstance().getPluginManager().callEvent(event);
	}

}
