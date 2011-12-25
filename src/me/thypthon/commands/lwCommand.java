/*
 * This file is made by Thypthon. And the idea is by Insane by Xstasy and Jcfk
 */

package me.thypthon.commands;

import me.thypthon.BC;
import me.thypthon.handlers.CommandHandler;
import me.thypthon.handlers.warns.WarningsHandler;

import org.bukkit.command.Command;
import org.bukkit.entity.Player;

public class lwCommand extends CommandHandler {

	private WarningsHandler wh;

	public lwCommand(BC instance) {
        super(instance);
        setStatus(1);
        this.wh = instance.getWarningsHandler();
        this.userHandler = instance.getUserHandler();
    }

    @Override
    public boolean onPlayerCommand(Player p, Command command, String label, String[] args) {
        // The user wants to see his own warns.
    	this.wh.listWarns(p);
        return true;
    }
}