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

import org.bukkit.Bukkit;
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
    			getUserWarnsCount.setInt(1, this.userHandler.getUIDFromDB(p));
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
			if(this.sqlHandler.update("INSERT INTO `warns`(`uid`, `by`, `reason`, `date`, `pos`) VALUES ('" + this.userHandler.getUIDFromDB(givens) + "', '" + this.userHandler.getUID(p) + "', '" + reason + "', UNIX_TIMESTAMP(), '"+px+", "+py+", "+pz+"')")){
				// Gi en beskjed.
				if(Bukkit.getServer().getPlayer(givens) != null){
					// ONLINE :D
					player.sendMessage(ChatColor.DARK_GREEN + "You have just got a warning!");
					player.sendMessage(ChatColor.DARK_GREEN + "Reason: " + ChatColor.WHITE + reason + ChatColor.GREEN + ", �2by " + ChatColor.WHITE + p.getName());
					p.sendMessage(ChatColor.GREEN + "You have just warned " + ChatColor.WHITE + player.getName() + ChatColor.GREEN + " a warning.");
					p.sendMessage(ChatColor.WHITE + player.getName() + ChatColor.GREEN + " has now " + ChatColor.WHITE + userWarnsOnline(player) + ChatColor.GREEN + " warnings.");
				} else {
					// oFFLINE :(
					p.sendMessage(ChatColor.RED + "The player is offline. So no live-notice is sent.");
					p.sendMessage(ChatColor.GREEN + "You have just given " + ChatColor.WHITE + givens + ChatColor.GREEN + " a warning.");
					p.sendMessage(ChatColor.WHITE + givens + ChatColor.GREEN + " has now " + ChatColor.WHITE + userWarns(givens) + ChatColor.GREEN + " warnings.");
				}
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

        	p.sendMessage(ChatColor.GOLD + "============================");
        	p.sendMessage(ChatColor.WHITE + p.getName() + ChatColor.DARK_GREEN + "'s warnings.");
        	int rowCount = userWarnsOnline(p);

        	if (rowCount == 0) {
        		p.sendMessage(ChatColor.WHITE + "no warnings found.");
        		return;
        	}

        	int i = 0;
        	while (rs.next()) {
        		i++;
        		// Fix for color errors
        		Date date = new Date(rs.getLong("date") * 1000);
				p.sendMessage(ChatColor.DARK_GREEN + "Warning:" + rs.getString(ChatColor.WHITE + "reason"));
			p.sendMessage(ChatColor.DARK_GREEN + "By:" + rs.getString(ChatColor.DARK_BLUE+ "by") + ChatColor.GREEN + ". Date: " + dateFormat.format(date) + ".");
				p.sendMessage(ChatColor.DARK_GREEN + "Location: " + rs.getString(ChatColor.WHITE + "pos") + ".");
				p.sendMessage(ChatColor.GOLD + "============================");
        	}
		p.sendMessage(ChatColor.DARK_GREEN + "Totalt :" + userWarnsOnline(p) + ChatColor.GREEN + "warnings.");
			p.sendMessage(ChatColor.GOLD + "============================");
    	} catch (SQLException e) {
            BC.log.log(Level.SEVERE, "[BC] Feil i WarningsHandler: ", e);
            return;
        }
    }
    
    public void listWarnsOthers(Player p, String reqs){
    	Player req = plugin.getServer().getOfflinePlayer(reqs).getPlayer();
    	Player onreq = plugin.playerMatch(reqs);
    	if(this.userHandler.getUserStatus(p) >= 5){
    		if(Bukkit.getServer().getPlayer(reqs) != null){
    			try {
    				//this.plugin.getServer().broadcastMessage("Player: " + p);
    				//int uid = this.userHandler.getUID(p);
    				//this.plugin.getServer().broadcastMessage("UID: " + uid);
        	
    				getUserWarns.setInt(1, this.userHandler.getUID(onreq));
    				ResultSet rs = getUserWarns.executeQuery();
    			
    				p.sendMessage(ChatColor.GOLD + "--------------------");
    				p.sendMessage(ChatColor.WHITE + req.getName() + ChatColor.DARK_GREEN + "'s warnings.");
    				int rowCount = userWarnsOnline(req);
    			
    				if (rowCount == 0) {
    					p.sendMessage(ChatColor.WHITE + "no warnings found.");
    					return;
    				}

    				int i = 0;
    				while (rs.next()) {
    					i++;
    					Date date = new Date(rs.getLong("date") * 1000);
    					p.sendMessage(ChatColor.DARK_GREEN + "Warning: " + rs.getString(ChatColor.WHITE + "reason"));
    					p.sendMessage(ChatColor.DARK_GREEN + "By: " + rs.getInt(ChatColor.BLUE + "by") + ". Date: " + dateFormat.format(date) + ".");
    					p.sendMessage(ChatColor.DARK_GREEN + "Location: " + rs.getString(ChatColor.WHITE + "pos") + ".");
    					p.sendMessage(ChatColor.GOLD + "--------------------");
    				}
    				p.sendMessage(ChatColor.DARK_GREEN + "Totalt " + userWarnsOnline(req) + "�2 	rnings.");
    				p.sendMessage(ChatColor.GOLD + "============================");
    			} catch (SQLException e) {
    				BC.log.log(Level.SEVERE, "[BC] Feil i WarningsHandler: ", e);
    				return;
    			}
    		} else {
    			try {
    				//this.plugin.getServer().broadcastMessage("Player: " + p);
    				//int uid = this.userHandler.getUID(p);
    				//this.plugin.getServer().broadcastMessage("UID: " + uid);
        	
    				getUserWarns.setInt(1, this.userHandler.getUIDFromDB(reqs));
    				ResultSet rs = getUserWarns.executeQuery();
    			
    				p.sendMessage(ChatColor.GOLD + "--------------------");
    				p.sendMessage(ChatColor.WHITE + reqs + ChatColor.DARK_GREEN + "'s warnings.");
    				int rowCount = userWarns(reqs);
    			
    				if (rowCount == 0) {
    					p.sendMessage(ChatColor.WHITE + "no warnings found.");
    					return;
    				}

    				int i = 0;
    				while (rs.next()) {
    					i++;
    					Date date = new Date(rs.getLong("date") * 1000);
    					p.sendMessage(ChatColor.DARK_GREEN + "Warning: " + rs.getString(ChatColor.WHITE + "reason"));
    					p.sendMessage(ChatColor.DARK_GREEN + "By: " + rs.getInt("by") + ChatColor.WHITE + " Date: " + dateFormat.format(date) + ".");
    				p.sendMessage(ChatColor.DARK_GREEN + "Location: " + rs.getString(ChatColor.WHITE + "pos") + ".");
    					p.sendMessage(ChatColor.GOLD + "--------------------");
    				}
    			p.sendMessage(ChatColor.DARK_GREEN + "Totalt " + userWarns(reqs) + ChatColor.DARK_GREEN +  " warnings.");
    				p.sendMessage(ChatColor.GOLD + "============================");
    			} catch (SQLException e) {
    				BC.log.log(Level.SEVERE, "[BC] Feil i WarningsHandler: ", e);
    				return;
    			}
    			p.sendMessage(ChatColor.RED + "BC-WarnHandler : Offline-player mode is stil in development.");
    		}
    	} else {
    		p.sendMessage(ChatColor.RED + "You do not have permission.");
    	}
    }
}
