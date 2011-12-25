package me.thypthon.handlers.warns;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import me.thypthon.BC;
import me.thypthon.handlers.users.PlayerData;
import me.thypthon.handlers.users.UserHandler;
import me.thypthon.handlers.utils.MySQLHandler;

public class WarningsHandler {
	private Connection conn;
	private HashMap<Player, PlayerData> users;
	private BC plugin;
	private PreparedStatement getUserWarns;
	private MySQLHandler sqlHandler;
	private UserHandler userHandler;
	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yy (HH:mm:ss)");
	private PreparedStatement getUserWarnsCount;

	public WarningsHandler(BC instance) {
        this.plugin = instance;
        this.sqlHandler = instance.getSqlHandler();
        this.users = new HashMap<Player, PlayerData>();
        this.userHandler = instance.getUserHandler();
    }

    public boolean initialize() {
        this.conn = this.sqlHandler.getConnection();
        try {
            this.getUserWarns = this.conn.prepareStatement("SELECT * FROM `warns` WHERE `uid` = ? LIMIT 4");
            this.getUserWarnsCount = this.conn.prepareStatement("SELECT COUNT(*) AS rowCount FROM `warns` WHERE `uid` = ?");
        } catch (SQLException e) {
            BC.log.log(Level.SEVERE, "[BC] Feil initialisering av prepared statements i WarningsHandler: ", e);
            return false;
        }
        return true;
    }
    
    public int userWarns(String p){
		int rowCount = 0;
    	Player player = plugin.getServer().getOfflinePlayer(p).getPlayer();
    	if (player != null) {
    		// HMM
    	} else {
    		try {
    			getUserWarnsCount.setInt(1, this.userHandler.getUID(player));
    			ResultSet rs = getUserWarnsCount.executeQuery();
    			if (rs.next()) {
    				rowCount = rs.getInt("rowCount");
    				rs.beforeFirst();
    			}
    		} catch (SQLException e) {
    			BC.log.log(Level.SEVERE, "[BC] Feil i WarningsHandler: ", e);
    		}
    	}
		return rowCount;
    }
    
    public int userWarnsOnline(Player p){
		int rowCount = 0;
    	if (p != null) {
    		// HMM
    	} else {
    		try {
    			getUserWarnsCount.setInt(1, this.userHandler.getUID(p));
    			ResultSet rs = getUserWarnsCount.executeQuery();
    			if (rs.next()) {
    				rowCount = rs.getInt("rowCount");
    				rs.beforeFirst();
    			}
    		} catch (SQLException e) {
    			BC.log.log(Level.SEVERE, "[BC] Feil i WarningsHandler: ", e);
    		}
    	}
		return rowCount;
    }
    
    public boolean giveWarn(Player p, String givens, String reason){
		if(reason != null){
			Player player = plugin.getServer().getOfflinePlayer(givens).getPlayer();

			Location pll = p.getLocation();
			double px = pll.getX();
			double py = pll.getY();
			double pz = pll.getZ();
			// ACTION \m/
			// Gi h*n en advarsel >:D
			if(this.sqlHandler.update("INSERT INTO `warns`(`uid`, `by`, `reason`, `date`, `pos`) VALUES ('" + this.userHandler.getUID(player) + "', '" + this.userHandler.getUID(p) + "', '" + reason + "', UNIX_TIMESTAMP(), '"+px+", "+py+", "+pz+"')")){
				// Gi en beskjed.
				if(player.isOnline()){
					player.sendMessage(ChatColor.DARK_GREEN + "You have just got a warning!");
					player.sendMessage(ChatColor.DARK_GREEN + "Reason: " + ChatColor.WHITE + reason + ChatColor.GREEN + ", §2by " + ChatColor.WHITE + p.getName());
				}
				p.sendMessage(ChatColor.GREEN + "You have just warned " + ChatColor.WHITE + player.getName() + ChatColor.GREEN + " a warning.");
				p.sendMessage(ChatColor.WHITE + player.getName() + ChatColor.GREEN + " has now " + ChatColor.WHITE + userWarns(givens) + ChatColor.GREEN + " warnings.");
				return true;
			}
		}
		return false;
    }
    
    public void listWarns(Player p){
        
        try {
            //this.plugin.getServer().broadcastMessage("Player: " + p);
            //int uid = this.userHandler.getUID(p);
            //this.plugin.getServer().broadcastMessage("UID: " + uid);
        	
        	getUserWarns.setInt(1, this.userHandler.getUID(p));
        	ResultSet rs = getUserWarns.executeQuery();

        	p.sendMessage("§6--------------------");
        	p.sendMessage(ChatColor.WHITE + p.getName() + ChatColor.DARK_GREEN + "'s warnings.");
        	int rowCount = userWarnsOnline(p);

        	if (rowCount == 0) {
        		p.sendMessage(ChatColor.WHITE + "no warnings found.");
        		return;
        	}

        	int i = 0;
        	while (rs.next()) {
        		i++;
        		Date date = new Date(rs.getLong("date") * 1000);
				p.sendMessage("§2Warning: §f" + rs.getString("reason"));
				p.sendMessage("§2By: §f" + this.userHandler.getNameFromUID(rs.getInt("by")) + "§2.§a Date: §f" + dateFormat.format(date) + "§a.");
				p.sendMessage("§2Location: §f" + rs.getString("pos") + "§2.");
				p.sendMessage("§6--------------------");
        	}
			p.sendMessage("§2Totalt §f" + userWarnsOnline(p) + "§2 warnings.");
			p.sendMessage("§6============================");
    	} catch (SQLException e) {
            BC.log.log(Level.SEVERE, "[BC] Feil i WarningsHandler: ", e);
            return;
        }
    }
    
    public void listWarnsOthers(Player p, String reqs){
    	Player req = plugin.getServer().getOfflinePlayer(reqs).getPlayer();
    	if(this.userHandler.getUserStatus(p) >= 5){
    		try {
    			//this.plugin.getServer().broadcastMessage("Player: " + p);
    			//int uid = this.userHandler.getUID(p);
    			//this.plugin.getServer().broadcastMessage("UID: " + uid);
        	
    			getUserWarns.setInt(1, this.userHandler.getUID(req));
    			ResultSet rs = getUserWarns.executeQuery();
    			
    			p.sendMessage("§6--------------------");
    			p.sendMessage(ChatColor.WHITE + req.getName() + ChatColor.DARK_GREEN + "'s warnings.");
    			int rowCount = userWarns(reqs);
    			
    			if (rowCount == 0) {
    				p.sendMessage(ChatColor.WHITE + "no warnings found.");
    				return;
    			}

    			int i = 0;
    			while (rs.next()) {
    				i++;
    				Date date = new Date(rs.getLong("date") * 1000);
    				p.sendMessage("§2Warning: §f" + rs.getString("reason"));
    				p.sendMessage("§2By: §f" + this.userHandler.getNameFromUID(rs.getInt("by")) + "§2.§a Date: §f" + dateFormat.format(date) + "§a.");
    				p.sendMessage("§2Location: §f" + rs.getString("pos") + "§2.");
    				p.sendMessage("§6--------------------");
    			}
    			p.sendMessage("§2Totalt §f" + userWarns(reqs) + "§2 warnings.");
    			p.sendMessage("§6============================");
    		} catch (SQLException e) {
    			BC.log.log(Level.SEVERE, "[BC] Feil i WarningsHandler: ", e);
    			return;
    		}
    	} else {
    		p.sendMessage(ChatColor.RED + "You do not have permission.");
    	}
    }
}
