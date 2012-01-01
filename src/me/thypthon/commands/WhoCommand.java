/*
 *   This file is part of BC.
 *
 *   BC is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   BC is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with BC.  If not, see <http://www.gnu.org/licenses/>.
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class WhoCommand extends CommandHandler {

    private UserHandler userHandler;

    public WhoCommand(BC instance) {
        super(instance);
        setStatus(0);
        this.userHandler = instance.getUserHandler();
    }

    @Override
    public boolean onPlayerCommand(Player p, Command command, String label, String[] args) {
        ConfigurationHandler cfg = plugin.getGlobalStateManager();
        WorldConfigurationHandler wcfg = cfg.get(p.getWorld());
        if (wcfg.WhoCommand) {
            HashMap<String, List> users = new HashMap<String, List>();
            List<String> admin = new ArrayList<String>();
            List<String> mod = new ArrayList<String>();
            List<String> user = new ArrayList<String>();
            List<String> guest = new ArrayList<String>();

            for(Player pf: plugin.getServer().getOnlinePlayers()) {
                int s = this.userHandler.getUserStatus(pf);
                if(s == BC.ADMIN) {
                    admin.add(pf.getName());
                } else if(s == BC.MOD) {
                    mod.add(pf.getName());
                } else if(s == BC.BUILDER) {
                    user.add(pf.getName());
                } else if(s == BC.GUEST) {
                    guest.add(pf.getName());
                }
            }
            users.put(BC.ADMIN_COLOR + "Admin", admin);
            users.put(BC.MOD_COLOR + "Mod", mod);
            users.put(BC.BUILDER_COLOR + "User", user);
            users.put(BC.GUEST_COLOR + "Guest", guest);

            p.sendMessage("§6==========={ §9Users online §6}===========");

            for(String s: users.keySet()) {
                List<String> l = users.get(s);
                if(l.size() > 0) {
                    p.sendMessage("===========");
                    p.sendMessage(s + " (" + l.size() + "):");
                    Collections.sort(l, String.CASE_INSENSITIVE_ORDER);
                    String list = "";
                    for(String name: l) {
                       list = list + name + ", ";
                    }
                     p.sendMessage(list.substring(0, list.length()-2) + ".");
                }
            }
            p.sendMessage("§6===================================");
            p.sendMessage(ChatColor.GREEN + "Online: " + ChatColor.WHITE + plugin.getServer().getOnlinePlayers().length);
        }
        return true;
    }
}