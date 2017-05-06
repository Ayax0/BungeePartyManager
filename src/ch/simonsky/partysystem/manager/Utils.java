package ch.simonsky.partysystem.manager;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Event;

public class Utils {
	
	public static void callEvent(Event event){
		ProxyServer.getInstance().getPluginManager().callEvent(event);
	}
	
	public static void sendPackInfo(ProxiedPlayer player, String name, String url, String hash) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("packChange");
        out.writeUTF(player.getName());
        out.writeUTF(name);
        out.writeUTF(url);
        out.writeUTF(hash);
        player.getServer().sendData("Resourcepack", out.toByteArray());
    }

}
