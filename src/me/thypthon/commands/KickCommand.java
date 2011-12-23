/*
* This file is part of Insane.
*
* Insane is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* Insane is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with Insane. If not, see <http://www.gnu.org/licenses/>.
* 
* Customised by Thypthon
*/

package me.thypthon.commands;


import me.thypthon.BC;
import me.thypthon.handlers.CommandHandler;
import me.thypthon.handlers.blocks.ActionID;
import me.thypthon.handlers.blocks.LogHandler;
import me.thypthon.handlers.config.ConfigurationHandler;
import me.thypthon.handlers.config.WorldConfigurationHandler;
import me.thypthon.handlers.users.UserHandler;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.bukkit.*;

public class KickCommand extends CommandHandler {

    private UserHandler userHandler;
    private LogHandler logHandler;
    private BC plugin;

    public KickCommand(BC instance) {
        super(instance);
        this.plugin = instance;
        setStatus(5);
        this.userHandler = instance.getUserHandler();
        this.logHandler = instance.getLogHandler();
    }

    @Override
    public boolean onPlayerCommand(Player p, Command command, String label, String[] args) {
        ConfigurationHandler cfg = plugin.getGlobalStateManager();
        WorldConfigurationHandler wcfg = cfg.get(p.getWorld());
        if (wcfg.KickCommand) {
            if (args.length >= 1) {
                Player v = plugin.playerMatch(args[0]);
                if (this.userHandler.getUserStatus(p) >= this.userHandler.getUserStatus(v)) {
                    if (args.length > 1) {
                        String kick = "";
                        for (int i = 1; i <= args.length - 1; i++) {
                            kick += args[i] + " ";
                        }
                        Location vl = v.getLocation();
                        kick = kick.substring(0, kick.length() - 1);
                        logHandler.addLine(this.userHandler.getUID(p), this.userHandler.getUID(v), ActionID.KICK, 0, kick);
                        this.plugin.broadcastAll(this.userHandler.getNameColor(v) + ChatColor.YELLOW + " kicked for : " + ChatColor.WHITE + kick);
                        this.plugin.broadcastAll(ChatColor.YELLOW + "By : " + this.userHandler.getNameColor(p));
                        v.kickPlayer(kick);
                        v.getWorld().strikeLightningEffect(vl);
                    } else {
                        logHandler.addLine(this.userHandler.getUID(p), this.userHandler.getUID(v), ActionID.KICK, 0, "NULL");
                        this.plugin.broadcastAll(this.userHandler.getNameColor(v) + ChatColor.YELLOW + " kicked for : " + ChatColor.WHITE + "No reason given, we do not need one.");
                        this.plugin.broadcastAll(ChatColor.YELLOW + "By : " + this.userHandler.getNameColor(p));
                        v.kickPlayer("No reason given, we do not need one.");
                    }
                } else {
                    p.sendMessage(ChatColor.RED + "You do not have permission to kick this user.");
                }
            }
        }
        return true;
    }
}