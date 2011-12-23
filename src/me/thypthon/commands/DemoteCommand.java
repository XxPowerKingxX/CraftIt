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

public class DemoteCommand extends CommandHandler {

	private UserHandler userHandler;

    public DemoteCommand(BC instance) {
        super(instance);
        setStatus(10);
        this.userHandler = instance.getUserHandler();
    }

    @Override
    public boolean onPlayerCommand(Player p, Command command, String label, String[] args) {
            if(args.length == 1) {
                Player v = plugin.playerMatch(args[0]);
                String name = v.getName();
                if(v != null) {
                	plugin.setUserStatus(name, 1);
                	plugin.broadcastAll(ChatColor.DARK_GREEN + "Userstatus to player " + ChatColor.WHITE + name + ChatColor.DARK_GREEN + " has been changed to " + ChatColor.WHITE + "user " + ChatColor.DARK_GREEN + "by " + ChatColor.WHITE + this.userHandler.getNameColor(p));
                }
            } else {
            	p.sendMessage(ChatColor.RED + "Plaese type in a username.");
            	p.sendMessage(ChatColor.WHITE + "Usage: " + ChatColor.GRAY + "/" + ChatColor.GOLD + "demote" + ChatColor.GRAY + " [" + ChatColor.GOLD + "username" + ChatColor.GRAY + "]");
            }
        return true;
    }
}