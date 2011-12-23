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
 * Time: 05:39
 */
public class AntiIgnition {
    @SuppressWarnings("unused")
	public AntiIgnition(PlayerInteractEvent e, BC plugin) {

        BlockProtect blockProtect = plugin.getBlockProtectHandler();
        BlockLog blockLog = plugin.getBlockLogHandler();
        UserHandler userHandler = plugin.getUserHandler();

        Player p = e.getPlayer();
        Block b = e.getClickedBlock();
        Action a = e.getAction();
        ItemStack iih = p.getItemInHand();

        // Anti-campfire & anti-portal
        if (iih.getType() == Material.FLINT_AND_STEEL && userHandler.getUserStatus(p) < 5 && a == Action.RIGHT_CLICK_BLOCK) {
            if (b != null) {
                if (b.getType() != Material.NETHERRACK) {
                    p.sendMessage(ChatColor.RED + "Du har ikke lov � tenne p� denne blokken, du kan tenne p� netherrack!");
                    e.setCancelled(true);
                }
            }
        }

    }
}