/*
 * This file is made by Thypthon. And the idea is by Insane by Xstasy and Jcfk
 */

package me.thypthon.commands;

import me.thypthon.BC;
import me.thypthon.handlers.CommandHandler;
import me.thypthon.handlers.groups.GroupHandler;

import org.bukkit.command.Command;
import org.bukkit.entity.Player;

public class GroupCommand extends CommandHandler {

	private GroupHandler groupHandler;

	public GroupCommand(BC instance) {
        super(instance);
        setStatus(1);
        this.groupHandler = instance.getGroupHandler();
        this.userHandler = instance.getUserHandler();
    }

    @Override
    public boolean onPlayerCommand(Player p, Command command, String label, String[] args) {

        String cmd = command.getName();

        	if(args[0].equalsIgnoreCase("new")){
        		this.groupHandler.newg(p, args[1]);
        		return true;
        	} else if(args[0].equalsIgnoreCase("leave")){
        		this.groupHandler.forlat(p);
        		return true;
        	} else if(args[0].equalsIgnoreCase("accept")){
        		this.groupHandler.accept(p);
        		return true;
        	} else if(args[0].equalsIgnoreCase("inv")){
                Player v = plugin.playerMatch(args[1]);
                String name = v.getName();
                if(v != null) {
                	this.groupHandler.invite(p, v);
                }
                return true;
        	}
    	
    	return false;
    }
}