/*
 * This file is made by Thypthon. And the idea is by Insane by Xstasy and Jcfk
 */

package me.thypthon.commands;

import me.thypthon.BC;
import me.thypthon.handlers.CommandHandler;
import me.thypthon.handlers.users.UserHandler;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

public class regCommand extends CommandHandler {

	private UserHandler userHandler;

    public regCommand(BC instance) {
        super(instance);
        setStatus(0);
        this.userHandler = instance.getUserHandler();
    }

    public boolean onPlayerCommand(Player p, Command command, String label, String[] args) {
    	if(this.userHandler.getUserStatus(p) == 0){
    		p.sendMessage(ChatColor.GOLD + "=============={ §9CraftIt /REG §6}==============");
    		p.sendMessage(ChatColor.GREEN + "1. §fGoto §awww.craftit.no §fand register an account.");
    		p.sendMessage(ChatColor.GREEN + "2. §fClick on §a\"§fBuilding permits§a\"§f and write an application.");
    		p.sendMessage("§a3.§f Say in-game to a §9mod§f/§6admin §fthat you have writen an application.");
    		p.sendMessage("§a4. §fFollow the rest.");
        	return true;
    	} else if(this.userHandler.getUserStatus(p) == 10){
    		p.sendMessage(ChatColor.RED + "(Command preview)");
    		p.sendMessage(ChatColor.GOLD + "=============={ §9CraftIt /REG §6}==============");
    		p.sendMessage(ChatColor.GREEN + "1. §fGoto §awww.craftit.no §fand register an account.");
    		p.sendMessage(ChatColor.GREEN + "2. §fClick on §a\"§fBuilding permits§a\"§f and write an application.");
    		p.sendMessage("§a3.§f Say in-game to a §9mod§f/§6admin §fthat you have writen an application.");
    		p.sendMessage("§a4. §fFollow the rest.");
        	return true;
    	} else {
    		p.sendMessage(ChatColor.RED + "You are not a guest.");
    	}
		return false;
    }
}