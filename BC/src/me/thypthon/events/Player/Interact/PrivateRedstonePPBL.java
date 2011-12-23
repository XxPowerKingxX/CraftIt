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
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Author: Xstasy
 * Date: 29.09.11
 * Time: 05:21
 */
public class PrivateRedstonePPBL {


    @SuppressWarnings("unused")
	public PrivateRedstonePPBL(PlayerInteractEvent e, BC plugin) {

        BlockProtect blockProtect = plugin.getBlockProtectHandler();
        BlockLog blockLog = plugin.getBlockLogHandler();
        UserHandler userHandler = plugin.getUserHandler();

        Player p = e.getPlayer();
        Block b = e.getClickedBlock();
        Action a = e.getAction();
        ItemStack iih = p.getItemInHand();

        // Private pressureplates, buttons og levers
        if(b != null) {
            if(b.getType() == Material.LEVER || b.getType() == Material.STONE_BUTTON ) {
                if(a == Action.LEFT_CLICK_BLOCK || a == Action.RIGHT_CLICK_BLOCK) {
                    Block u = b.getRelative(0, -2, 0);
                    if(u.getState() instanceof Sign) {
                        Sign s = (Sign) u.getState();
                        if(s.getLine(0).equalsIgnoreCase("privat")) {
                            if(blockProtect.getOwner(u) != userHandler.getUID(p)) {
                                e.setCancelled(false);
                            } else {
                                u.setType(Material.REDSTONE_TORCH_ON);
                            }
                        }
                    } else if(u.getType() == Material.REDSTONE_TORCH_ON) {
                        if(blockProtect.getOwner(u) == userHandler.getUID(p)) {
                            u.setType(Material.SIGN_POST);
                            Sign s = (Sign) u.getState();
                            s.setLine(0, "privat");
                        } else {
                            e.setCancelled(true);
                        }
                    }
                }
            } else if(b.getType() == Material.STONE_PLATE || b.getType() == Material.WOOD_PLATE) {
                if(a == Action.PHYSICAL) {
                    Block u = b.getRelative(0, -2, 0);
                    if(u.getState() instanceof Sign) {
                        Sign s = (Sign) u.getState();
                        if(s.getLine(0).equalsIgnoreCase("privat")) {
                            if(blockProtect.getOwner(u) != userHandler.getUID(p)) {
                                e.setCancelled(false);
                            } else {
                                u.setType(Material.REDSTONE_TORCH_ON);
                            }
                        }
                    }
                }
            }
        }
    }
}