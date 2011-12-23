package me.thypthon.handlers.groups;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;

import me.thypthon.BC;
import me.thypthon.handlers.users.PlayerData;
import me.thypthon.handlers.users.UserHandler;
import me.thypthon.handlers.utils.MySQLHandler;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class GroupHandler {
	@SuppressWarnings("unused")
	private HashMap<Player, PlayerData> users;
    private MySQLHandler sqlHandler;
    @SuppressWarnings("unused")
	private BC plugin;
    private Connection conn;
	@SuppressWarnings("unused")
	private PreparedStatement getGroupInfoById;
	private UserHandler userHandler;
	private PreparedStatement searchGroupName;

    public GroupHandler(BC instance) {
        this.plugin = instance;
        this.sqlHandler = instance.getSqlHandler();
        this.userHandler = instance.getUserHandler();
        this.users = new HashMap<Player, PlayerData>();
    }

    public boolean initialize() {
        this.conn = this.sqlHandler.getConnection();
        try {
            this.getGroupInfoById = this.conn.prepareStatement("SELECT * FROM `group` WHERE `id` = ?");
            this.searchGroupName = this.conn.prepareStatement("SELECT * FROM `group` WHERE `name` = ?");
        } catch (SQLException e) {
            BC.log.log(Level.SEVERE, "[BC] Feil initialisering av prepared statements i UserHandler: ", e);
            return false;
        }
        return true;
    }
    
    public void newg(Player p, String name){
    	if(name == ""){
    		p.sendMessage(ChatColor.RED + "Please enter a groupname.");
    		return;
    	}
    	
    	if(groupExists(name)){
    		if(this.sqlHandler.update("INSERT INTO `group`(`name`, `eier`) VALUES ('" + name + "', '" + this.userHandler.getUID(p) + "')")){
    			p.sendMessage(ChatColor.GREEN + "Group " + ChatColor.WHITE + name + ChatColor.GREEN + " has been created.");
    			p.sendMessage(ChatColor.GREEN + "Invite people to the group, just type: " + ChatColor.WHITE + "/gr inv [username]");
    			this.userHandler.setGroup(p.getName(), name, 1);
    		} else if(groupExists(name) == true) {
    			p.sendMessage(ChatColor.RED + "A group with the name " + ChatColor.WHITE + name + ChatColor.RED + " already exists.");
    		} else {
    			p.sendMessage(ChatColor.RED + "Could not create group, contact a mod/admin.");
    		}
    	}
    }
    
    public boolean invite(Player p, Player inv){    	
    	if(this.userHandler.getUserGroup(inv) != "None"){
    		// Bruker er ikke en gruppe.
    		String invitingusergroup = this.userHandler.getUserGroup(p.getName());
    		
    		inv.sendMessage(ChatColor.WHITE + p.getName() + ChatColor.GREEN + " invited you to a group.");
    		inv.sendMessage(ChatColor.GREEN + "Type " + ChatColor.WHITE + "/gr accept" + ChatColor.GREEN + " to join the group.");
    		
    		// Set the group to inviting player
    		this.userHandler.setGroup(inv.getName(), invitingusergroup, 0);
    		
    		return true;
    	} else if(this.userHandler.getUserGroup(inv.getName()) != "None"){
    		// Brukeren er i en gruppe.
    		p.sendMessage(ChatColor.WHITE + inv.getName() + ChatColor.RED + " is already in a group.");
    		return true;
    	}
		return false;
    }
    
    public void forlat(Player p){
    	if(this.userHandler.getUserGroup(p) != "None"){
    		// Brukeren er ikke i "NONE" gruppa.
    		// Da setter vi h*n til NONE gruppa.
    		this.userHandler.setGroup(p, "None", 1);
    	} else {
    		// Brukeren er medlem i "None".
    		// Da kan h*n ikke forlate gruppa.
    		p.sendMessage(ChatColor.RED + "You are not in a group yet.");
    	}
    }
    
    public boolean accept(Player p){
    	if(this.userHandler.getUserGroup(p) != "None"){
    		// Brukern er ikke i "None" gruppa.
    		// Da kan h*n joine en gruppe :D
    		this.userHandler.setGroupGodt(p, 1);
    		return true;
    	} else {
    		// Brukeren er medlem i "None".
    		// Da kan h*n ikke forlate gruppa.
    		p.sendMessage(ChatColor.RED + "You do not have an invite.");
    		return true;
    	}
    }

    public boolean groupExists(String name) {
    		boolean exist = false;
    		String uname = name;
    		try {
    			this.searchGroupName.setString(1, uname);

    			ResultSet rs = this.searchGroupName.executeQuery();

    			while (rs.next()) {
    				exist = true;
    			}
    		} catch (SQLException e) {
    			BC.log.log(Level.SEVERE, "[BC] MySQL Error: " + Thread.currentThread().getStackTrace()[0].getMethodName(), e);
    		} catch(NullPointerException e){
    			BC.log.log(Level.SEVERE, "[BC] Error: " + Thread.currentThread().getStackTrace()[0].getMethodName(), e);
    		} 
    		return exist;
    }
    
    public boolean getGroupInfo(){
    	try {
    		this.getGroupInfoById = this.conn.prepareStatement("SELECT * FROM group WHERE `id` = ?");
    	} catch (SQLException e) {
            BC.log.log(Level.SEVERE, "[BC] Feil initialisering av prepared statements i UserHandler: ", e);
            return false;
        }
		return false;
    }
}
