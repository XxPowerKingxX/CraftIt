/*
 * This file is made by Thypthon. And the idea is by Insane by Xstasy and Jcfk
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

public class BlockProtectionSlime {
	public BlockProtectionSlime(PlayerInteractEvent e, BC plugin) {
        
        BlockProtect blockProtect = plugin.getBlockProtectHandler();
        BlockLog blockLog = plugin.getBlockLogHandler();
        UserHandler userHandler = plugin.getUserHandler();

        Player p = e.getPlayer();
        Block b = e.getClickedBlock();
        Action a = e.getAction();
        ItemStack iih = p.getItemInHand();
        
        // Adminstick
        if ((iih.getType() == Material.SLIME_BALL) && (userHandler.getUserStatus(p) >= 5)) {
            // Fjerner blokken og returnerer item
            if (a == Action.LEFT_CLICK_BLOCK) {
                // Internal Error-fiks.
                if (b.getType() == Material.AIR) return;

                blockProtect.add(p, b);
                blockLog.log(p, b, 5, b.getType().name());
                p.sendMessage(ChatColor.GREEN + "Block is now protected to you.");

                // Fjerner blokken og retunerer ingenting.
            } else if (a == Action.RIGHT_CLICK_BLOCK) {
                blockProtect.addtoServerUser(p, b);
                blockLog.log(p, b, 5, b.getType().name());
            } else {
                return;
            }
        }
	}
}
