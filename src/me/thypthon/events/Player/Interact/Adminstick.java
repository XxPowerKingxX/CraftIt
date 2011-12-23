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
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class Adminstick {
    public Adminstick(PlayerInteractEvent e, BC plugin) {
        
        BlockProtect blockProtect = plugin.getBlockProtectHandler();
        BlockLog blockLog = plugin.getBlockLogHandler();
        UserHandler userHandler = plugin.getUserHandler();

        Player p = e.getPlayer();
        Block b = e.getClickedBlock();
        Action a = e.getAction();
        ItemStack iih = p.getItemInHand();
        
        // Adminstick
        if ((iih.getType() == Material.STICK) && (userHandler.getUserStatus(p) >= 5)) {
            // Fjerner blokken og returnerer item
            if (a == Action.LEFT_CLICK_BLOCK) {
                // Internal Error-fiks.
                if (b.getType() == Material.AIR) return;

                ItemStack n = new ItemStack(b.getType(), 1, b.getData());
                p.getInventory().addItem(n);
                blockProtect.delete(b);
                blockLog.log(p, b, 2, b.getType().name());
                b.setType(Material.AIR);

                // Fjerner blokken og retunerer ingenting.
            } else if (a == Action.RIGHT_CLICK_BLOCK) {
                blockProtect.delete(b);
                blockLog.log(p, b, 2, b.getType().name());
                b.setType(Material.AIR);

            } else {
                return;
            }
        }
    }
}