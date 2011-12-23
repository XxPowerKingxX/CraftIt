/*
 * This file is made by Thypthon. And the idea is by Insane by Xstasy and Jcfk
 */

package me.thypthon.commands;

import me.thypthon.BC;
import me.thypthon.handlers.CommandHandler;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

public class BCCommand extends CommandHandler {

    public BCCommand(BC instance) {
        super(instance);
        setStatus(5);
        this.userHandler = instance.getUserHandler();
    }

    @Override
    public boolean onPlayerCommand(Player p, Command command, String label, String[] args) {
    	String msg = args[0];
    	if(args.length == 1) {
    		plugin.broadcastAll(ChatColor.RED + "[Broadcast] " + ChatColor.GREEN + msg);
    		return true;
    	}
		return false;
    }
}