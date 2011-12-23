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

public class BrukerCommand extends CommandHandler {

    private UserHandler userHandler;

    public BrukerCommand(BC instance) {
        super(instance);
        setStatus(0);
        this.userHandler = instance.getUserHandler();
    }

    @Override
    public boolean onPlayerCommand(Player p, Command command, String label, String[] args) {
        ConfigurationHandler cfg = plugin.getGlobalStateManager();
        WorldConfigurationHandler wcfg = cfg.get(p.getWorld());
        if (wcfg.BrukerCommand) {
            p.sendMessage(ChatColor.WHITE + "------------ Userinfo ------------");
            p.sendMessage(ChatColor.WHITE + "ID: " + ChatColor.GREEN + this.userHandler.getUID(p));
            p.sendMessage(ChatColor.WHITE + "Username: " + ChatColor.GREEN + p.getName());
            p.sendMessage(ChatColor.WHITE + "Status: " + ChatColor.GREEN + this.userHandler.getUserStatus(p));
        }
        return true;
    }
}