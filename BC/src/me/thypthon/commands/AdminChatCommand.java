/*
 * This file is made by Thypthon. And the idea is by Insane by Xstasy and Jcfk
 */

package me.thypthon.commands;

import me.thypthon.BC;
import me.thypthon.handlers.CommandHandler;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

public class AdminChatCommand extends CommandHandler {

    public AdminChatCommand(BC instance) {
        super(instance);
        setStatus(5);
        this.userHandler = instance.getUserHandler();
    }

    @Override
    public boolean onPlayerCommand(Player p, Command command, String label, String[] args) {
    		String msg = "";
    		for (int i = 0; i <= args.length - 1; i++) {
    			msg += args[i] + " ";
    		}
    		msg = msg.substring(0, msg.length() - 1);
    		plugin.broadcastAdminChat(ChatColor.WHITE + "(" + p.getName() + ChatColor.WHITE + ") " + ChatColor.WHITE + msg);
    		return true;
    }
}