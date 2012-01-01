package me.thypthon.commands;

import me.thypthon.BC;
import me.thypthon.BC;
import me.thypthon.handlers.BankHandler;
import me.thypthon.handlers.CommandHandler;
import me.thypthon.handlers.users.UserHandler;

import org.bukkit.command.Command;
import org.bukkit.entity.Player;

public class iCommand extends CommandHandler {
	private UserHandler userHandler;
	private BankHandler bank;
	
	public iCommand(BC instance) {
        super(instance);
        setStatus(1);
        this.userHandler = instance.getUserHandler();
	}
	
	@Override
    public boolean onPlayerCommand(Player p, Command command, String label, String[] args) {
		if(this.userHandler.getUserStatus(p) >= 5){
		}
		return false;
	}
}
