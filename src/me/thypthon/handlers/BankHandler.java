package me.thypthon.handlers;

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
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import me.thypthon.BC;
import me.thypthon.handlers.users.PlayerData;
import me.thypthon.handlers.users.UserHandler;
import me.thypthon.handlers.utils.MySQLHandler;

public class BankHandler {
	private Connection conn;
	private HashMap<Player, PlayerData> users;
	private BC plugin;
	private PreparedStatement getUserMoney;
	private MySQLHandler sqlHandler;
	private UserHandler userHandler;
	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yy (HH:mm:ss)");
	private PreparedStatement updateUserMoney;

	public BankHandler(BC instance) {
        this.plugin = instance;
        this.sqlHandler = instance.getSqlHandler();
        this.users = new HashMap<Player, PlayerData>();
        this.userHandler = instance.getUserHandler();
    }

    public boolean initialize() {
        this.conn = this.sqlHandler.getConnection();
        try {
            this.getUserMoney = this.conn.prepareStatement("SELECT * FROM `users` WHERE `id` = ?");
            this.updateUserMoney = this.conn.prepareStatement("UPDATE `users` SET `gold` = ? WHERE `id` = ?");
        } catch (SQLException e) {
            BC.log.log(Level.SEVERE, "[BC] Feil initialisering av prepared statements i BankHandler: ", e);
            return false;
        }
        return true;
    }
    
    public int getGold(Player p){
    	int gold = 0;
    	try {
    		this.getUserMoney.setInt(1, this.userHandler.getUID(p));
    		ResultSet rs = this.getUserMoney.executeQuery();
    		
    		while(rs.next()){
    			gold = rs.getInt("gold");
    		}
    		
    	} catch (SQLException e) {
            BC.log.log(Level.SEVERE, "[BC] Feil initialisering av prepared statements i WarningsHandler: ", e);
            return 0;
        }
		return gold;
    }
    
    public boolean insertGold(Player p){
    	int itemID = 266;
    	int amount = 0;
    	try {
    		PlayerInventory pi = p.getInventory();
    		ItemStack items[] = pi.getContents();
    		
    		if (pi.contains(itemID)) {

    			// Loop through player's inventory to look for <item>
    			for (ItemStack item : items) {
    			// if the inventory slot is not null AND it is the item we are looking for then change amount
    				if (item != null && item.getTypeId() == itemID) amount += item.getAmount();
    				pi.remove(Material.GOLD_INGOT);
    			}
    			
    			int have_money = this.getGold(p);
    			int newg = have_money + amount;
    			this.updateUserMoney.setInt(1, newg);
    			this.updateUserMoney.setInt(2, this.userHandler.getUID(p));
    			this.updateUserMoney.executeUpdate();
    			
    			p.sendMessage(ChatColor.DARK_GREEN + "You just inserted " + ChatColor.WHITE + amount + ChatColor.DARK_GREEN + " goldbars.");
    			p.sendMessage(ChatColor.DARK_GREEN + "Total in the bank " + ChatColor.WHITE + newg);
    			
    			return true; // finally say we've handled command correctly
    		} else {
    			p.sendMessage(ChatColor.RED + "You don't have any goldbars");
    			return true;
    		}
    	} catch (SQLException e) {
            BC.log.log(Level.SEVERE, "[BC] Feil initialisering av prepared statements i WarningsHandler: ", e);
            return false;
        }
    }
    
    public boolean takeoutGold(Player p, int amount){
    	PlayerInventory pi = p.getInventory(); // The player's inventory
    	try {
    		int have_money = this.getGold(p);
    		if(amount == 0 || amount == 1){
    			amount = have_money;
    		}
    		if(have_money >= amount || have_money == amount){ // The user has enouhg
    			int newg = have_money - amount;
    			this.updateUserMoney.setInt(1, newg);
    			this.updateUserMoney.setInt(2, this.userHandler.getUID(p));
    			this.updateUserMoney.executeUpdate();
    			
    			ItemStack diamondstack = new ItemStack(Material.GOLD_INGOT, amount); // A stack of diamonds
                pi.addItem(diamondstack); // Adds a stack of diamonds to the player's inventory
    		
    			p.sendMessage(ChatColor.DARK_GREEN + "You just took out " + ChatColor.WHITE + amount + ChatColor.DARK_GREEN + " goldbars.");
    			p.sendMessage(ChatColor.DARK_GREEN + "Total in the bank " + ChatColor.WHITE + newg);
    			return true;
    		} else {
    			if(have_money <= amount){
    				p.sendMessage(ChatColor.RED + "You do not have so mutch in the bank.");
    				return true;
    			}
    		}
    	} catch (SQLException e) {
            BC.log.log(Level.SEVERE, "[BC] Feil initialisering av prepared statements i WarningsHandler: ", e);
            return false;
        }
		return false;
    }
    
    public boolean infoBank(Player p){
    	int have_money = this.getGold(p);
    	p.sendMessage(ChatColor.DARK_GREEN + "In bank " + ChatColor.WHITE + have_money + ChatColor.DARK_GREEN + " goldbars.");
    	return true;
    }
    
    public boolean main(Player p){
    	p.sendMessage("§6=============={ §9CraftIt Bank, Inc. §6}==============");
    	p.sendMessage("§fCommands that have options with [] is §crequierd§f, if () its §7optional§f. Avabilble commands:");
    	p.sendMessage("§f-§8/§6bank in");
    	p.sendMessage("§f-§8/§6bank out §8[§famount§8]");
    	p.sendMessage("§f-§8/§6bank info");
		return true;
    }
}