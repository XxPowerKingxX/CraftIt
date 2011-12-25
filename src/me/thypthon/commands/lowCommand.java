/*
 * This file is made by Thypthon. And the idea is by Insane by Xstasy and Jcfk
 */

package me.thypthon.commands;

import me.thypthon.BC;
import me.thypthon.handlers.CommandHandler;
import me.thypthon.handlers.warns.WarningsHandler;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

public class lowCommand extends CommandHandler {

	private WarningsHandler wh;

	public lowCommand(BC instance) {
        super(instance);
        setStatus(5);
        this.wh = instance.getWarningsHandler();
        this.userHandler = instance.getUserHandler();
    }

    @Override
    public boolean onPlayerCommand(Player p, Command command, String label, String[] args) {
        if(args[0] == ""){
        	p.sendMessage(ChatColor.RED + "Please write a username");
        	// The user wants to see his own warns.
        } else {
        	this.wh.listWarnsOthers(p, args[0]);
        	return true;
        }
		return false;
    }
}