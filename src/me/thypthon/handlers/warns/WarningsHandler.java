package me.thypthon.handlers.warns;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;

import org.bukkit.ChatColor;
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

	public WarningsHandler(BC instance) {
        this.plugin = instance;
        this.sqlHandler = instance.getSqlHandler();
        this.users = new HashMap<Player, PlayerData>();
    }

    public boolean initialize() {
        this.conn = this.sqlHandler.getConnection();
        try {
            this.getUserWarns = this.conn.prepareStatement("SELECT * FROM `warns` WHERE `uid` = ?");
        } catch (SQLException e) {
            BC.log.log(Level.SEVERE, "[BC] Feil initialisering av prepared statements i WarningsHandler: ", e);
            return false;
        }
        return true;
    }
    
    public int userWarns(Player p){
    	int rowCount = -1;
    	try {
    		this.getUserWarns.setInt(1, this.userHandler.getUID(p));
    		ResultSet rs = this.getUserWarns.executeQuery();

    		rs.next();
    	    rowCount = rs.getInt(1);
    	} catch (SQLException e) {
            BC.log.log(Level.SEVERE, "[BC] Feil i WarningsHandler: ", e);
        }
		return rowCount;
    }
    
    public boolean giveWarn(Player p, Player given, String reason){
		if(reason != null){
			// ACTION \m/
			// Gi h*n en advarsel >:D
    		if(this.sqlHandler.update("INSERT INTO `warns`(`uid`, `by`, `reason`, `date`, `pos`) VALUES ('" + given.getName() + "', '" + this.userHandler.getUID(p) + "', '" + reason + "', UNIX_TIMESTAMP(), '" + p.getLocation() + "')")){
    			// Gi en beskjed.
    			given.sendMessage(ChatColor.GREEN + "You have just got a warning!");
    			given.sendMessage(ChatColor.GREEN + "Reason: " + ChatColor.WHITE + reason + ChatColor.GREEN + ", by " + ChatColor.WHITE + p.getName());
    			p.sendMessage(ChatColor.GREEN + "You have just warned " + ChatColor.WHITE + given.getName() + ChatColor.GREEN + " a warning.");
    			p.sendMessage(ChatColor.WHITE + given.getName() + ChatColor.GREEN + " has now " + ChatColor.WHITE + userWarns(p) + ChatColor.GREEN + " warnings.");
    			return true;
    		}   
		}
    	return false;
    }
    
    public boolean listWarns(Player p){
    	int rowCount = -1;
    	ResultSet rs = null;
        ArrayList<String> row = new ArrayList<String>();
        
    	try {
    		if(sqlHandler.update("SELECT * FROM users WHERE "))
    		this.getUserWarns.setInt(1, this.userHandler.getUID(p));
    		this.getUserWarns.execute();
    		rs = this.getUserWarns.getResultSet();

    		rowCount = rs.getInt(1);
			p.sendMessage(ChatColor.WHITE + p.getName() + ChatColor.DARK_GREEN + " warnings.");
			while (rs.next()) {
				if(rowCount == 0){
					p.sendMessage(ChatColor.WHITE + "no warnings found.");
				} else {
					Date date = new Date(rs.getLong("date") * 1000);
					p.sendMessage("§2Warning: §f " + rs.getString("reason"));
					p.sendMessage("§2By: §f" + rs.getInt("by") + "§2.§a Date: §f" + date + "§a.");
					p.sendMessage("§2Location: §f" + rs.getString("pos") + "§2.");
					p.sendMessage("§6--------------------");
				}
			}
			p.sendMessage("§2Total §f" + rowCount + "§2 warnings.");
			p.sendMessage("§6========{ §9The END §6}========");
			return true;
    	} catch (SQLException e) {
            BC.log.log(Level.SEVERE, "[BC] Feil i WarningsHandler: ", e);
            return false;
        }
    }
    
    public ArrayList<String> getWarnings(Block b) {

        ResultSet rs = null;
        ArrayList<String> row = new ArrayList<String>();

        try {
            this.getUserWarns.setShort(1, (short) b.getX());
            this.getUserWarns.execute();
            rs = this.getUserWarns.getResultSet();


            while (rs.next()) {

                Date date = new Date(rs.getLong("time") * 1000);
                row.add("[" + dateFormat.format(date) + "]");
            }
            rs.close();

        } catch (SQLException e) {
            BC.log.log(Level.SEVERE, "[BC] SQL Exception i getBlockLog ", e);
        }
        return row;
    }
    
    public boolean listWarnsOthers(Player p, Player req){
    	int rowCount = -1;
    	if(userHandler.getUserStatus(p) >= 5){
    		try {
    			this.getUserWarns.setString(1, req.getName());
    			ResultSet rs = this.getUserWarns.executeQuery();
    			
    			rowCount = rs.getInt(1);
    			p.sendMessage(ChatColor.WHITE + req.getName() + ChatColor.DARK_GREEN + " warnings.");
    			while (rs.next()) {
    				if(rowCount == 0){
    					p.sendMessage(ChatColor.WHITE + "no warnings found.");
    				} else {
    					Date date = new Date(rs.getLong("date") * 1000);
    					p.sendMessage("§2Warning: §f " + rs.getString(3));
    					p.sendMessage("§2By: §f" + rs.getString(4) + "§2.§a Satt: §f" + date + "§a.");
    					p.sendMessage("§2Location: §f" + rs.getString("pos") + "§2.");
    					p.sendMessage("§6--------------------");
    				}
    			}
    			p.sendMessage("§2Total §f" + rowCount + "§2 warnings.");
    			p.sendMessage("§6========{ §9The END §6}========");
    		} catch (SQLException e) {
    			BC.log.log(Level.SEVERE, "[BC] Feil i WarningsHandler: ", e);
    		}
    	} else {
    		p.sendMessage(ChatColor.RED + "You do not have permission to see others warnings.");
    	}
    	return false;
    }
}
