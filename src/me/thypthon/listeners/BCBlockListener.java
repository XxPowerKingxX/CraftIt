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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import me.thypthon.BC;
import me.thypthon.handlers.blocks.BlockLog;
import me.thypthon.handlers.blocks.BlockProtect;
import me.thypthon.handlers.config.ConfigurationHandler;
import me.thypthon.handlers.config.WorldConfigurationHandler;
import me.thypthon.handlers.users.UserHandler;
import me.thypthon.mech.RedstoneRemote.RedstoneRemote;
import me.thypthon.mech.RedstoneRemote.RedstoneRemoteData;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockFormEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.inventory.ItemStack;

public class BCBlockListener extends BlockListener {
	private ArrayList<Location> ra = new ArrayList<Location>();
    private Set<Material> blocks;

    private BC plugin;
    private UserHandler userHandler;
    private BlockProtect blockProtect;
    private BlockLog blockLog;
    private RedstoneRemote rrh;

    public BCBlockListener(BC instance) {
        this.plugin = instance;
        this.userHandler = instance.getUserHandler();
        this.blockProtect = instance.getBlockProtectHandler();
        this.blockLog = instance.getBlockLogHandler();
        this.rrh = instance.getRedstoneRemote();
        blocks = new HashSet<Material>();
        blocks.add(Material.REDSTONE_WIRE);
        blocks.add(Material.REDSTONE_TORCH_ON);
        blocks.add(Material.REDSTONE_TORCH_OFF);
        blocks.add(Material.DIODE_BLOCK_OFF);
        blocks.add(Material.DIODE_BLOCK_ON);
        blocks.add(Material.LEVER);
        blocks.add(Material.STONE_BUTTON);
        blocks.add(Material.RAILS);
        blocks.add(Material.TORCH);
    }


    public void onBlockForm(BlockFormEvent e) {
        World w = e.getBlock().getWorld();

        ConfigurationHandler cfg = this.plugin.getGlobalStateManager();
        WorldConfigurationHandler wcfg = cfg.get(w);

        if ((!e.isCancelled()) && (e.getNewState().getType() == Material.ICE) && (!wcfg.IceRegen)) {
            e.setCancelled(true);
        } else if ((!e.isCancelled()) && (e.getNewState().getType() == Material.SNOW) && (!wcfg.SnowRegen)) {
            e.setCancelled(true);
        }

    }

    public void onBlockFromTo(BlockFromToEvent event) {
        if (blocks.contains(event.getToBlock().getType())) {
            event.setCancelled(true);
        }
    }

    public void onSignChange(SignChangeEvent e) {
        if (e.getLine(0).equalsIgnoreCase("mottaker")) {
            if (e.getLine(1).length() > 0) {
                this.rrh.setReciever(e.getBlock(), e.getLine(1));
            }
        }
    }

    public void onBlockPlace(BlockPlaceEvent e) {
        Player p = e.getPlayer();
        Block b = e.getBlock();

        // Kista - logg
        if (b.getType() == Material.LOCKED_CHEST && this.userHandler.getUserStatus(p) >= 5) {
            e.setCancelled(true);
            e.setBuild(true);

            p.sendMessage(ChatColor.BLUE + "------ " + ChatColor.WHITE + "Log for: X: " + b.getX() + " Y: " + b.getY() + " Z: " + b.getZ() + " WORLD: " + b.getWorld().getName() + ChatColor.BLUE + " -------");
            for (String line : this.blockLog.getBlockLog(b)) {
                p.sendMessage(line);
            }

            return;
        }
        
        // TNT utplassering
        if(b.getType() == Material.TNT){
        	p.sendMessage(ChatColor.RED + "Hmm, this is an TNT.... not allowed through the border. So heres a cake");
        	b.setType(Material.CAKE_BLOCK);
        }
        
        // Lava plassering
        if(b.getType() == Material.LAVA){
        	if(this.userHandler.getUserStatus(p) == 0){
        		p.sendMessage("§cContact a mod/admin for that.");
        		b.setType(Material.AIR);
        		e.setCancelled(true);
        	} else if (this.userHandler.getUserStatus(p) == 1){
        		p.sendMessage("§cContact a mod/admin for that.");
        		b.setType(Material.AIR);
        		e.setCancelled(true);
        	}
        }
        
        // Vann plassering
        if(b.getType() == Material.WATER){
        	if(this.userHandler.getUserStatus(p) == 0){
        		p.sendMessage("§cContact a mod/admin for that.");
        		b.setType(Material.AIR);
        		e.setCancelled(true);
        	} else if (this.userHandler.getUserStatus(p) == 1){
        		p.sendMessage("§cContact a mod/admin for that.");
        		b.setType(Material.AIR);
        		e.setCancelled(true);
        	}
        }
        
        if(b.getType() == Material.WATER_BUCKET){
        	if(this.userHandler.getUserStatus(p) == 0){
        		p.sendMessage("§cContact a mod/admin for that.");
        		b.setType(Material.AIR);
        		e.setCancelled(true);
        	} else if (this.userHandler.getUserStatus(p) == 1){
        		p.sendMessage("§cContact a mod/admin for that.");
        		b.setType(Material.AIR);
        		e.setCancelled(true);
        	}
        }
        
        // Flamme plassering
        if(b.getType() == Material.FLINT_AND_STEEL){
        	if(this.userHandler.getUserStatus(p) == 0 || this.userHandler.getUserStatus(p) == 1){
        		p.sendMessage("§cContact a mod/admin for that.");
        		b.setType(Material.AIR);
        		e.setCancelled(true);
        	}
        }

        // Kreves det tillatelse for bygging og kan brukeren bygge?
        if (!this.userHandler.canBuild(p)) {
            e.setCancelled(true);
        } else {
            if (!this.blockProtect.add(p, b)) {
                e.setBuild(false);
                e.setCancelled(true);
            }
        }
    }

    public void onBlockBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        Block b = e.getBlock();
        // Kreves det tillatelse for bygging og kan brukeren bygge?
        if (!this.userHandler.canBuild(p)) {
            e.setCancelled(true);
            return;
        } else {
            if (!this.blockProtect.delete(p, b)) {
                e.setCancelled(true);
                return;
            }
        }

        // Glassblokker dropper glass-item.
        if (b.getType() == Material.GLASS) {
            b.getWorld().dropItemNaturally(b.getLocation(), new ItemStack(Material.GLASS, 1));
        }

        // Glowstone-blokker dropper glowstone-item i stedet for glowstone dust.
        if (b.getType() == Material.GLOWSTONE) {
            e.setCancelled(true);
            b.setType(Material.AIR);
            b.getWorld().dropItemNaturally(b.getLocation(), new ItemStack(Material.GLOWSTONE, 1));
        }
    }
    public class redstoneTickRemove implements Runnable {
        private Block b;
        public redstoneTickRemove(Block b) {
            this.b = b;
        }

        public void run() {
            b.setType(Material.SIGN_POST);
            Sign s = (Sign) b.getState();
            s.setLine(0, "privat");
        }
    }

    @SuppressWarnings("rawtypes")
    public void onBlockRedstoneChange(BlockRedstoneEvent event) {
		Block block = event.getBlock();

        // Pressureplates og buttons må bytte til skilt igjen her (privat)
        if(!block.isBlockIndirectlyPowered() || !block.isBlockPowered()) {
            if(block.getType() == Material.STONE_BUTTON || block.getType() == Material.WOOD_PLATE || block.getType() == Material.STONE_PLATE) {
                Block u = block.getRelative(0, -2, 0);
                if(u.getType() == Material.REDSTONE_TORCH_ON) {
                     this.plugin.getServer().getScheduler().scheduleAsyncDelayedTask(plugin, new redstoneTickRemove(u), 19L);
                }
            }
        }

		// Redstone fence gates
		BlockFace[] faces = {BlockFace.NORTH,BlockFace.SOUTH,BlockFace.EAST,BlockFace.WEST};
		for(BlockFace face : faces) {
			Block faceBlock = block.getRelative(face);
			if (faceBlock.getType() == Material.FENCE_GATE) {
				byte data = faceBlock.getData();
				if (!block.isBlockPowered() && !block.isBlockIndirectlyPowered()) {
					if (ra.contains(faceBlock.getLocation()) || faceBlock.getData() >= 4) { continue; }
					ra.add(faceBlock.getLocation());
					data += 4;
				} else {
					if (faceBlock.getData() < 4) { continue; }
					ra.remove(faceBlock.getLocation());
					data -= 4;
				}
				faceBlock.setData(data);
			}
		}

        // Redstone Remote
        if (block.getState() instanceof Sign) {
            Sign sign = (Sign) event.getBlock().getState();
            if (sign.getLine(0).equalsIgnoreCase("sender")) {
                if (sign.getLine(1).length() > 0) {
                    String name = sign.getLine(1);
                    if (this.rrh.hasReciever(name)) {
                        ArrayList<RedstoneRemoteData> mottakere = this.rrh.getRecievers(name);
                        Iterator itr = mottakere.iterator();
                        RedstoneRemoteData rrrd = null;
                        boolean remove = false;
                        while (itr.hasNext()) {
                            RedstoneRemoteData rrd = (RedstoneRemoteData) itr.next();
                            Block b = this.plugin.getServer().getWorld(block.getWorld().getName()).getBlockAt(rrd.getLocation().getLocation(this.plugin));
                            if ((b.getType() == Material.REDSTONE_TORCH_ON) || (b.getState() instanceof Sign)) {
                                if ((block.isBlockPowered()) || (block.isBlockIndirectlyPowered())) {
                                    b.setType(Material.REDSTONE_TORCH_ON);
                                } else if (b.getType() == Material.REDSTONE_TORCH_ON) {
                                    b.setType(Material.AIR);
                                    b.setTypeIdAndData(rrd.getMaterial().getId(), rrd.getData(), true);
                                    Sign signset = (Sign) b.getState();
                                    signset.setLine(0, "mottaker");
                                    signset.setLine(1, name);
                                }
                            } else {
                                rrrd = rrd;
                                remove = true;
                            }
                        }
                        if (remove) {
                            this.rrh.removeReciever(name, rrrd);
                        }
                    }
                } else {
                    return;
                }
            } else {
                return;
            }
        }
    }
}