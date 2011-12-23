/*
 *   This file is part of Insane.
 *
 *   Insane is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   Insane is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with Insane.  If not, see <http://www.gnu.org/licenses/>.
 */

package me.thypthon.commands;

import me.thypthon.BC;
import me.thypthon.handlers.CommandHandler;
import me.thypthon.handlers.config.ConfigurationHandler;
import me.thypthon.handlers.config.WorldConfigurationHandler;
import me.thypthon.handlers.users.UserHandler;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

/**
 * Author: Xstasy
 * Date: 30.09.11
 * Time: 00:20
 */
public class MessageCommand extends CommandHandler {
     private UserHandler userHandler;

    public MessageCommand(BC instance) {
        super(instance);
        setStatus(0);
        this.userHandler = instance.getUserHandler();
    }

    @Override
    public boolean onPlayerCommand(Player p, Command command, String label, String[] args) {
        ConfigurationHandler cfg = plugin.getGlobalStateManager();
        WorldConfigurationHandler wcfg = cfg.get(p.getWorld());
        if (wcfg.MessageCommand) {
            if(args.length >= 2) {
                String message = "";
                for(int i = 1; i < args.length; i++) {
                    message = message + args[i] + " ";
                }
                message = message.substring(0, message.length()-1);
                Player v = plugin.playerMatch(args[0]);
                if(v != null) {
                    p.sendMessage(ChatColor.DARK_AQUA + "==> " + p.getName() + ": " + ChatColor.DARK_AQUA + message);
                    v.sendMessage(ChatColor.DARK_AQUA + "<== " + p.getName() + ": " + ChatColor.DARK_AQUA + message);
                } else {
                    p.sendMessage(ChatColor.RED + "Brukeren er avlogget eller eksisterer ikke.");
                }
            }
        }
        return true;
    }
}
