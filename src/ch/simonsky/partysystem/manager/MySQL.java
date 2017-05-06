package ch.simonsky.partysystem.manager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import net.md_5.bungee.api.connection.ProxiedPlayer;


public class MySQL {
	
	public static Connection con;
	
	public static void connect(String host, String port, String database, String username, String password){
		try {
			if(!isConnected()){
				con = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, username, password);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void close(){
		if(isConnected()){
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static boolean isConnected(){
		return con != null;
	}
	
	public static void createTable(){
		if(isConnected()){
			try {
				con.createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS Partyrequest_toggle (UUID VARCHAR(100), Name VARCHAR(100), state BOOLEAN)");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void toggleState(ProxiedPlayer player){
		try {
			if(!doesPlayerExists(player)){
				PreparedStatement ps = con.prepareStatement("INSERT INTO Partyrequest_toggle (UUID,Name,state) VALUES (?,?,?)");
				ps.setString(1, player.getUniqueId().toString());
				ps.setString(2, player.getName());
				ps.setBoolean(3, false);
				
				ps.executeUpdate();
			}else{
				PreparedStatement ps = con.prepareStatement("UPDATE Partyrequest_toggle SET state = ? WHERE UUID = ?");
				ps.setBoolean(1, !getState(player));
				ps.setString(2, player.getUniqueId().toString());
				
				ps.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean doesPlayerExists(ProxiedPlayer player){
		try {
			PreparedStatement ps = con.prepareStatement("SELECT state FROM Partyrequest_toggle WHERE UUID = ?");
			ps.setString(1, player.getUniqueId().toString());
			ResultSet rs = ps.executeQuery();
			
			return rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static boolean getState(ProxiedPlayer player){
		try {
			PreparedStatement ps = con.prepareStatement("SELECT state FROM Partyrequest_toggle WHERE UUID = ?");
			ps.setString(1, player.getUniqueId().toString());
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()){
				return rs.getBoolean("state");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}
