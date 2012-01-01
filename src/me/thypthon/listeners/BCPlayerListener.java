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

package me.thypthon.listeners;

import me.thypthon.BC;
import me.thypthon.events.Player.Interact.*;
import me.thypthon.handlers.blocks.BlockLog;
import me.thypthon.handlers.blocks.BlockProtect;
import me.thypthon.handlers.config.ConfigurationHandler;
import me.thypthon.handlers.users.UserHandler;
import me.thypthon.handlers.warns.WarningsHandler;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.*;
import org.bukkit.util.Vector;
import org.schwering.irc.lib.IRCConstants;

public class BCPlayerListener extends PlayerListener {

    private BC plugin;
    private UserHandler userHandler;
	private BlockProtect bp;
	private BlockLog blocklog;
	private WarningsHandler wh;

    public BCPlayerListener(BC instance) {
        this.plugin = instance;
        this.userHandler = instance.getUserHandler();
        this.bp = instance.getBlockProtectHandler();
        this.blocklog = instance.getBlockLogHandler();
        this.wh = instance.getWarningsHandler();
    }

    public void onPlayerLogin(PlayerLoginEvent e) {
        ConfigurationHandler cfg = this.plugin.getGlobalStateManager();
        Player p = e.getPlayer();
        if (!cfg.ispublic) {
            if (this.userHandler.getPlayerData(p).getStatus() < 5) {
            	e.setKickMessage("The server is not yet public.");
                e.disallow(PlayerLoginEvent.Result.KICK_OTHER, "The server is not public.");
                this.userHandler.logout(p);
            }
        }
    }

    public void onPlayerMove(PlayerMoveEvent e) {
        ConfigurationHandler cfg = this.plugin.getGlobalStateManager();
        Player p = e.getPlayer();
        if (p.isSneaking() && cfg.gold_boots) {
            if (p.getInventory().getBoots().getAmount() > 0) {
                if ((Material.GOLD_BOOTS.equals(p.getInventory().getBoots().getType())) && (this.userHandler.getUserStatus(p) >= cfg.gold_boots_access)) {
                    Vector v = p.getLocation().getDirection();
                    v.multiply(cfg.gold_boots_multiplier);
                    p.setVelocity(v);
                }
            }
        }
    }

	public void onPlayerJoin(PlayerJoinEvent e) {
    	ConfigurationHandler cfg = this.plugin.getGlobalStateManager();
        Player p = e.getPlayer();
        this.userHandler.reloadUser(p);
        this.userHandler.login(p);
        p.sendMessage(ChatColor.GOLD + "================{ " + ChatColor.BLUE + "CraftIt Buildserver " + ChatColor.GOLD + "}================");
        if(this.userHandler.getUserStatus(p.getName()) == BC.GUEST){
        	p.sendMessage(ChatColor.WHITE + "Goto" + ChatColor.GREEN + " www.craftit.no" + ChatColor.WHITE + " and send in a permit to get building permission.");
        } else {
        	p.sendMessage(ChatColor.WHITE + "Goto "+ ChatColor.GREEN +"www.craftit.no "+ ChatColor.WHITE +"for more info.");
        }
        if(this.wh.userWarnsOnline(p) == 0){
        	// Ingen advarsler.
        } else {
        	p.sendMessage(ChatColor.DARK_GREEN + "You have " + ChatColor.WHITE + this.wh.userWarnsOnline(p) + ChatColor.DARK_GREEN + " warnings!");
        }
        e.setJoinMessage(this.userHandler.getNameColor(p) + ChatColor.GREEN + " logged in.");
        plugin.getIRC().getIRCConnection().doPrivmsg(cfg.botchannel, userHandler.getIRCNameColor(e.getPlayer().getName()) + IRCConstants.COLOR_INDICATOR + "3" + " logged in." + IRCConstants.COLOR_END_INDICATOR);
    }

	public void onPlayerQuit(PlayerQuitEvent e) {
    	ConfigurationHandler cfg = this.plugin.getGlobalStateManager();
    	Player p = e.getPlayer();
        this.userHandler.logout(e.getPlayer());
        e.setQuitMessage(this.userHandler.getNameColor(p) + ChatColor.RED + " logged out.");
        plugin.getIRC().getIRCConnection().doPrivmsg(cfg.botchannel, userHandler.getIRCNameColor(e.getPlayer().getName()) + IRCConstants.COLOR_INDICATOR + "4" + " logged out." + IRCConstants.COLOR_END_INDICATOR);
    }

	public void onPlayerChat(PlayerChatEvent e) {
		ConfigurationHandler cfg = this.plugin.getGlobalStateManager();
        e.setCancelled(true);
        if(this.userHandler.getUserStatus(e.getPlayer()) == 10){
        	this.plugin.broadcastAll(this.userHandler.getNameColor(e.getPlayer()) + ChatColor.GOLD + ": " + ChatColor.WHITE + e.getMessage());
        } else if(this.userHandler.getUserStatus(e.getPlayer()) == 5){
        	this.plugin.broadcastAll(this.userHandler.getNameColor(e.getPlayer()) + ChatColor.BLUE + ": " + ChatColor.WHITE + e.getMessage());
        } else if(this.userHandler.getUserStatus(e.getPlayer()) == 1){
        	this.plugin.broadcastAll(this.userHandler.getNameColor(e.getPlayer()) + ": " + ChatColor.WHITE +  e.getMessage());
        } else if(this.userHandler.getUserStatus(e.getPlayer()) == 0){
        	this.plugin.broadcastAll(this.userHandler.getNameColor(e.getPlayer()) + ChatColor.GRAY + ": " + ChatColor.WHITE + e.getMessage());
        }
        BCIRCListener irc = plugin.getIRC();
        irc.getIRCConnection().doPrivmsg(cfg.botchannel, this.userHandler.getIRCNameColor(e.getPlayer().getName()) + ": " + e.getMessage());
    }

    public void onPlayerKick(PlayerKickEvent e) {
        if (e.getReason().equals("You moved too quickly :( (Hacking?)")) {
            e.setCancelled(true);
            return;
        }
        if ((e.getReason().toLowerCase().contains("flying")) || (e.getReason().toLowerCase().contains("floating"))) {
            e.setCancelled(true);
        }
        e.setLeaveMessage(null);
    }

    public void onPlayerInteract(PlayerInteractEvent e) {
    	Player p = e.getPlayer();
    	Material b = e.getMaterial();
    	
        // Private Redstone Pressureplates, buttons og levers.
        new PrivateRedstonePPBL(e, plugin);

        // Anti-crop ødeleggelse
        new AntiCropsTrembling(e, plugin);

        // Adminstick
        new Adminstick(e, plugin);

        // Anti-påtenning med lighter (unntak: netherrack)
        new AntiIgnition(e, plugin);

        // LogClock - Blokklogg via klokka.
        new LogClock(e, plugin);

        // Evigvarende verktøy
        new InfinityTools(e, plugin);

        // Plassering av gjerde på siden av andre blokker.
        new FenceEverywhere(e, plugin);
        
        // Protection
        new BlockProtectionSlime(e, plugin);
        
        // Paper
        new PaperBlockProtection(e, plugin);
        
        // Vann plassering
        if(p.getItemInHand().getType() == Material.WATER_BUCKET){
        	if(this.userHandler.getUserStatus(p) == 0){
        		p.sendMessage("§cContact a mod/admin for that.");
        		e.setCancelled(true);
        	} else if (this.userHandler.getUserStatus(p) == 1){
        		p.sendMessage("§cContact a mod/admin for that.");
        		e.setCancelled(true);
        	}
        }
        
        // Vann plassering
        if(p.getItemInHand().getType() == Material.FLINT_AND_STEEL){
        	if(this.userHandler.getUserStatus(p) == 0){
        		p.sendMessage("§cContact a mod/admin for that.");
        		e.setCancelled(true);
        	} else if (this.userHandler.getUserStatus(p) == 1){
        		p.sendMessage("§cContact a mod/admin for that.");
        		e.setCancelled(true);
        	}
        }
        
        // Vann plassering
        if(p.getItemInHand().getType() == Material.LAVA_BUCKET){
        	if(this.userHandler.getUserStatus(p) == 0){
        		p.sendMessage("§cContact a mod/admin for that.");
        		e.setCancelled(true);
        	} else if (this.userHandler.getUserStatus(p) == 1){
        		p.sendMessage("§cContact a mod/admin for that.");
        		e.setCancelled(true);
        	}
        }
        
        // Chest Protection
        if (e.getAction() != Action.LEFT_CLICK_BLOCK && e.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }
        if (e.getAction() == Action.LEFT_CLICK_BLOCK) {
        	return;
        } else if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
        	Block block = e.getClickedBlock();
        	int uid = this.userHandler.getUID(p);
        	String uid_group = this.userHandler.getUserGroup(p);
        	if(block.getType() == Material.CHEST){
        		int owner = bp.getOwner(block);
                String owner_name = this.userHandler.getNameFromUID(owner);
                String owner_group = this.userHandler.getUserGroup(owner_name);
                
                if (owner == uid || owner_group == uid_group && owner_group != "None" && uid_group != "None") {
        			blocklog.log(uid, block, 3, block.getType().name());
        		} else if(userHandler.getUserStatus(p) >= 5){
        			blocklog.log(uid, block, 3, block.getType().name());
        		} else if(owner != uid){
        			p.sendMessage(ChatColor.RED + "This is not your chest.");
        			e.setCancelled(true);
        		} else {
        			p.sendMessage(ChatColor.RED + "This is not your chest.");
        			e.setCancelled(true);
        		}
        	}
        }
        
        // Furnace Protection
        if (e.getAction() != Action.LEFT_CLICK_BLOCK && e.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }
        if (e.getAction() == Action.LEFT_CLICK_BLOCK) {
        	return;
        } else if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
        	Block block = e.getClickedBlock();
        	int uid = this.userHandler.getUID(p);
        	String uid_group = this.userHandler.getUserGroup(p);
        	if(block.getType() == Material.FURNACE){
        		int owner = bp.getOwner(block);
                String owner_name = this.userHandler.getNameFromUID(owner);
                String owner_group = this.userHandler.getUserGroup(owner_name);
                
                if (owner == uid || owner_group == uid_group && owner_group != "None" && uid_group != "None") {
        			blocklog.log(uid, block, 3, block.getType().name());
        		} else if(userHandler.getUserStatus(p) >= 5){
        			blocklog.log(uid, block, 3, block.getType().name());
        		} else if(owner != uid){
        			p.sendMessage(ChatColor.RED + "This is not your furnace.");
        			e.setCancelled(true);
        		} else {
        			p.sendMessage(ChatColor.RED + "This is not your furnace.");
        			e.setCancelled(true);
        		}
        	}
        }
        
        // Dispenser Protection
        if (e.getAction() != Action.LEFT_CLICK_BLOCK && e.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }
        if (e.getAction() == Action.LEFT_CLICK_BLOCK) {
        	return;
        } else if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
        	Block block = e.getClickedBlock();
        	int uid = this.userHandler.getUID(p);
        	String uid_group = this.userHandler.getUserGroup(p);
        	if(block.getType() == Material.DISPENSER){
        		int owner = bp.getOwner(block);
                String owner_name = this.userHandler.getNameFromUID(owner);
                String owner_group = this.userHandler.getUserGroup(owner_name);
                
                if (owner == uid || owner_group == uid_group && owner_group != "None" && uid_group != "None") {
        			blocklog.log(uid, block, 3, block.getType().name());
        		} else if(userHandler.getUserStatus(p) >= 5){
        			blocklog.log(uid, block, 3, block.getType().name());
        		} else if(owner != uid){
        			p.sendMessage(ChatColor.RED + "This is not your dispenser.");
        			e.setCancelled(true);
        		} else {
        			p.sendMessage(ChatColor.RED + "This is not your dispenser.");
        			e.setCancelled(true);
        		}
        	}
        }
        
        // Workbench Protection
        if (e.getAction() != Action.LEFT_CLICK_BLOCK && e.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }
        if (e.getAction() == Action.LEFT_CLICK_BLOCK) {
        	return;
        } else if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
        	Block block = e.getClickedBlock();
        	int uid = this.userHandler.getUID(p);
        	String uid_group = this.userHandler.getUserGroup(p);
        	if(block.getType() == Material.WORKBENCH){
        		int owner = bp.getOwner(block);
                String owner_name = this.userHandler.getNameFromUID(owner);
                String owner_group = this.userHandler.getUserGroup(owner_name);
                
                if (owner == uid || owner_group == uid_group && owner_group != "None" && uid_group != "None") {
        			blocklog.log(uid, block, 3, block.getType().name());
        		} else if(userHandler.getUserStatus(p) >= 5){
        			blocklog.log(uid, block, 3, block.getType().name());
        		} else if(owner != uid){
        			p.sendMessage(ChatColor.RED + "This is not your workbench.");
        			e.setCancelled(true);
        		} else {
        			p.sendMessage(ChatColor.RED + "This is not your workbench.");
        			e.setCancelled(true);
        		}
        	}
        }          
    }
}