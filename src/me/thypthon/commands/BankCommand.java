package me.thypthon.commands;

import me.thypthon.BC;
import me.thypthon.handlers.BankHandler;
import me.thypthon.handlers.CommandHandler;
import me.thypthon.handlers.users.UserHandler;

import org.bukkit.command.Command;
import org.bukkit.entity.Player;

public class BankCommand extends CommandHandler {
	private UserHandler userHandler;
	private BankHandler bank;

    public BankCommand(BC instance) {
        super(instance);
        setStatus(1);
        this.bank = instance.getBankHandler();
        this.userHandler = instance.getUserHandler();
    }

    @Override
    public boolean onPlayerCommand(Player p, Command command, String label, String[] args) {
    	int amount = 0;
    	if (args.length == 0){
    		this.bank.main(p);
    		return true;
    	}
    	if (args.length == 1 && args[0].equalsIgnoreCase("in")) {
    		// Insert
    		amount = this.bank.getGold(p);
    		this.bank.insertGold(p);
    		plugin.getIRC().getIRCConnection().doPrivmsg("#craftit.bank", p.getName() + " satt inn " + amount);
    		return true;
    	} else if (args[0].equalsIgnoreCase("out")) {
    		// Take
    		if (args.length == 2) {
    			amount = Integer.parseInt(args[1]);
    		} else if (args.length == 1) {
    			amount = this.bank.getGold(p);
    		}
    		this.bank.takeoutGold(p, amount);
    		plugin.getIRC().getIRCConnection().doPrivmsg("#craftit.bank", p.getName() + " tokk ut " + amount);
    		return true;
    	} else if (args.length == 1 && args[0].equalsIgnoreCase("info")) {
    		this.bank.infoBank(p);
    		return true;
    	}
    	
		return false;
    }
}
