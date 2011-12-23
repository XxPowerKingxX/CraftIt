/*
 * This file is made by Thypthon. And the idea is by Insane by Xstasy and Jcfk
 */

package me.thypthon.commands;

import me.thypthon.BC;
import me.thypthon.handlers.CommandHandler;
import me.thypthon.handlers.config.ConfigurationHandler;
import me.thypthon.handlers.config.WorldConfigurationHandler;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

public class StuckCommand extends CommandHandler {


    public StuckCommand(BC instance) {
        super(instance);
        setStatus(0);
    }

    public boolean onPlayerCommand(Player p, Command command, String label, String[] args) {
        ConfigurationHandler cfg = plugin.getGlobalStateManager();
        WorldConfigurationHandler wcfg = cfg.get(p.getWorld());
        if (wcfg.StuckCommand) {
            Block b = p.getWorld().getHighestBlockAt(p.getLocation().getBlockX(), p.getLocation().getBlockZ());
            Location l = new Location(p.getWorld(), b.getX() + 0.5, b.getY(), b.getZ() + 0.5, p.getLocation().getYaw(), p.getLocation().getPitch());
            p.teleport(l);
        }
        return true;
    }
}