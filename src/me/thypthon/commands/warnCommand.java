/*
 * This file is made by Thypthon. And the idea is by Insane by Xstasy and Jcfk
 */

package me.thypthon.commands;

import me.thypthon.BC;
import me.thypthon.handlers.CommandHandler;
import me.thypthon.handlers.warns.WarningsHandler;

import org.bukkit.command.Command;
import org.bukkit.entity.Player;

public class warnCommand extends CommandHandler {

	private WarningsHandler wh;

	public warnCommand(BC instance) {
        super(instance);
        setStatus(5);
        this.wh = instance.getWarningsHandler();
        this.userHandler = instance.getUserHandler();
    }

    @Override
    public boolean onPlayerCommand(Player p, Command command, String label, String[] args) {        
        if (args.length >= 1) {
            if (this.userHandler.getUserStatus(p) >= 5) {
                if (args.length > 1) {
                    String kick = "";
                    for (int i = 1; i <= args.length - 1; i++) {
                        kick += args[i] + " ";
                    }
                    if(this.wh.giveWarn(p, args[0], kick)){
                    	return true;
                    }
                    return true;
                }
            }
        }
    	return false;
    }
}