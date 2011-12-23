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

package me.thypthon.events.Player.Interact;

import me.thypthon.BC;
import me.thypthon.handlers.blocks.BlockLog;
import me.thypthon.handlers.blocks.BlockProtect;
import me.thypthon.handlers.users.UserHandler;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Author: Xstasy
 * Date: 29.09.11
 * Time: 05:42
 */
public class LogClock {
    public LogClock(PlayerInteractEvent e, BC plugin) {

        BlockProtect blockProtect = plugin.getBlockProtectHandler();
        BlockLog blockLog = plugin.getBlockLogHandler();
        UserHandler userHandler = plugin.getUserHandler();

        Player p = e.getPlayer();
        Block b = e.getClickedBlock();
        Action a = e.getAction();
        ItemStack iih = p.getItemInHand();

        // Klokka - logg
        if (iih.getType() == Material.WATCH && userHandler.getUserStatus(p) >= 5 && a == Action.RIGHT_CLICK_BLOCK) {
            p.sendMessage(ChatColor.BLUE + "-------- " + ChatColor.WHITE + "Log for: X: " + b.getX() + " Y: " + b.getY() + " Z: " + b.getZ() + " WORLD: " + b.getWorld().getName() + ChatColor.BLUE + " ---------");

            if (blockProtect.isProtected(b)) {
                for (String line : blockLog.getBlockLog(b)) {
                    p.sendMessage(line);
                }
            } else {
            	p.sendMessage(ChatColor.RED + "This block is not protected.");
            }
            String username = userHandler.getNameFromUID(blockProtect.getOwner(b));
            if(username == "-1" || username == "0"){
            	p.sendMessage(ChatColor.BLUE + "Owner: " + ChatColor.WHITE + "no owner");
            } else {
            	p.sendMessage(ChatColor.BLUE + "Owner: " + ChatColor.WHITE + username);
            }
        }

    }
}