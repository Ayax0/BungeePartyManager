package ch.simonsky.partysystem.manager;

import java.io.File;
import java.io.IOException;

import ch.simonsky.partysystem.Main;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class MySQLConfig {
	
	public static Configuration config;
	public static File file;
	
	public static void createIfNotExists(){
		try {
			if(!Main.instance.getDataFolder().exists()){
				Main.instance.getDataFolder().mkdir();
			}
			file = new File(Main.instance.getDataFolder().getPath(), "config.yml");
			if(!file.exists()){
				file.createNewFile();
				
				Configuration c = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
				c.set("Username", "root");
				c.set("Passwort", "toor");
				c.set("Host", "localhost");
				c.set("Port", "3306");
				c.set("Database", "PartySystem");
				ConfigurationProvider.getProvider(YamlConfiguration.class).save(c, file);
			}
			
			config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unused")
	private static void save(){
		try {
			ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String getUsername(){
		return config.getString("Username");
	}
	
	public static String getPasswort(){
		return config.getString("Passwort");
	}
	
	public static String getHost(){
		return config.getString("Host");
	}
	
	public static String getPort(){
		return config.getString("Port");
	}
	
	public static String getDatabase(){
		return config.getString("Database");
	}

}
