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

public class AddUserCommand extends CommandHandler {

    @SuppressWarnings("unused")
	private UserHandler userHandler;

    public AddUserCommand(BC instance) {
        super(instance);
        setStatus(5);
    }

    @Override
    public boolean onPlayerCommand(Player p, Command command, String label, String[] args) {
            if(args.length == 1) {
                Player v = plugin.playerMatch(args[0]);
                String name = v.getName();
                if(v != null) {
                	plugin.setUserStatus(name, 1);
                	p.sendMessage(ChatColor.DARK_GREEN + "Player " + ChatColor.WHITE + v.getName() + ChatColor.DARK_GREEN + " is now added.");
                    v.sendMessage(ChatColor.DARK_GREEN + "You do now have building permission.");
                }
            } else {
            	p.sendMessage(ChatColor.RED + "Plaese type in a username.");
            	p.sendMessage(ChatColor.WHITE + "Usage: " + ChatColor.GRAY + "/" + ChatColor.GOLD + "add" + ChatColor.GRAY + " [" + ChatColor.GOLD + "username" + ChatColor.GRAY + "]");
            }
        return true;
    }
}