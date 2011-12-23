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

package me.thypthon.listeners;

import me.thypthon.BC;
import me.thypthon.handlers.blocks.BlockLog;
import me.thypthon.handlers.blocks.BlockProtect;
import me.thypthon.handlers.blocks.LogAction;
import me.thypthon.handlers.config.ConfigurationHandler;
import me.thypthon.handlers.config.WorldConfigurationHandler;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.entity.*;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

public class BCEntityListener extends EntityListener {

    private final BC plugin;
    private BlockProtect blockprotect;
    private BlockLog blocklog;

    public BCEntityListener(BC instance) {
        this.plugin = instance;
        this.blockprotect = instance.getBlockProtectHandler();
        this.blocklog = instance.getBlockLogHandler();

    }

    // Ikke ødelegge crops
    public void onEntityInteract(EntityInteractEvent e) {
        if (e.getBlock().getType() == Material.SOIL) {
            e.setCancelled(true);
        }
    }

    public void onEntityDamage(EntityDamageEvent event) {
        World world = event.getEntity().getWorld();
        Entity attacker = null;
        Entity defender = event.getEntity();
        DamageCause type = event.getCause();

        ConfigurationHandler cfg = this.plugin.getGlobalStateManager();
        WorldConfigurationHandler wcfg = cfg.get(world);
        if ((!wcfg.Damage) || (!wcfg.PVPDamage) || (!wcfg.CreatureDamage)) {
            if (event instanceof EntityDamageByEntityEvent) {
                EntityDamageByEntityEvent subEvent = (EntityDamageByEntityEvent) event;
                attacker = subEvent.getDamager();
                if ((defender instanceof Player) && (!wcfg.PVPDamage) && (attacker instanceof Player) || (defender instanceof Player) && (attacker instanceof Creature || attacker instanceof Monster) && (!wcfg.CreatureDamage)) {
                    event.setCancelled(true);
                    return;
                }
            }
        }

        if (type == DamageCause.DROWNING) {
            if ((!wcfg.Damage) || (!wcfg.DrowningDamage)) {
                event.setCancelled(true);
            }
        } else if (type == DamageCause.CONTACT) {
            if ((!wcfg.Damage) || (!wcfg.ContactDamage)) {
                event.setCancelled(true);
            }
        } else if (type == DamageCause.ENTITY_EXPLOSION) {
            if (!wcfg.Damage || (attacker instanceof Creature) && (!wcfg.CreatureDamage)) {
                event.setCancelled(true);
            } else if ((!wcfg.Damage) || !(attacker instanceof LivingEntity) && (!wcfg.TNTDamage)) {
                event.setCancelled(true);
            }
        } else if (type == DamageCause.FALL) {
            if ((!wcfg.Damage) || (!wcfg.FallDamage)) {
                event.setCancelled(true);
            }
        } else if (type == DamageCause.SUFFOCATION) {
            if ((!wcfg.Damage) || (!wcfg.BlockDamage)) {
                event.setCancelled(true);
            }
        } else if (type == DamageCause.FIRE) {
            if ((!wcfg.Damage) || (!wcfg.FireDamage)) {
                event.setCancelled(true);
            }
        } else if (type == DamageCause.FIRE_TICK) {
            if ((!wcfg.Damage) || (!wcfg.FireDamage)) {
                event.setCancelled(true);
            }
        } else if (type == DamageCause.LAVA) {
            if ((!wcfg.Damage) || (!wcfg.LavaDamage)) {
                event.setCancelled(true);
            }
        } else if (type == DamageCause.STARVATION) {
            if ((!wcfg.Damage) || (!wcfg.Hunger)) {
                event.setCancelled(true);
                if (event.getEntity() instanceof Player) {
                    Player p = (Player) event.getEntity();
                    p.setFoodLevel(20);
                }
            }
        } else if (type == DamageCause.VOID) {
            if ((!wcfg.Damage) || (!wcfg.VoidDamage)) {
                event.setCancelled(true);
                if (event.getEntity() instanceof Player) {
                    Player player = (Player) event.getEntity();
                    if (wcfg.VoidTeleport) {
                        player.teleport(event.getEntity().getWorld().getSpawnLocation());
                    }
                }
            }
        }

        if (attacker instanceof Player) {

        }

        if (defender instanceof Player) {

        }

        if (defender instanceof Player) {
            if (!event.isCancelled() && !wcfg.Damage) {
                event.setCancelled(true);
            }
        }

    }

    public void onEntityDamageByBlock(EntityDamageByBlockEvent event) {

    }

    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {

    }

    public void onEndermanPickup(EndermanPickupEvent e) {
        World w = e.getBlock().getWorld();
        Block b = e.getBlock();

        ConfigurationHandler cfg = this.plugin.getGlobalStateManager();
        WorldConfigurationHandler wcfg = cfg.get(w);
        if (this.blockprotect.isProtected(b)) {
            if (wcfg.EndermenPickup && wcfg.EndermenPickupProtected) {
                this.blockprotect.delete(b);
                this.blocklog.log(BC.ENDERMEN_UID, b, LogAction.DELETE, b.getType().name());
            } else {
                e.setCancelled(true);
            }
        } else {
            if (!wcfg.EndermenPickup) {
                e.setCancelled(true);
            } else {
                this.blocklog.log(BC.ENDERMEN_UID, b, LogAction.DELETE, b.getType().name());
            }
        }
    }

    public void onEntityExplode(EntityExplodeEvent event) {
        Location l = event.getLocation();
        Entity e = event.getEntity();
        World world = l.getWorld();

        ConfigurationHandler cfg = this.plugin.getGlobalStateManager();
        WorldConfigurationHandler wcfg = cfg.get(world);

        if (e instanceof LivingEntity) {
            if ((e instanceof Creeper) && (!wcfg.CreeperBlockDamage)) {
                event.setCancelled(true);
            }
        } else {
            if (!wcfg.TNTBlockDamage) {
                event.setCancelled(true);
            } else if (!wcfg.TNTBlockDrops) {
                event.setYield(0);
            }
        }

    }

    public void onCreatureSpawn(CreatureSpawnEvent event) {
        CreatureType type = event.getCreatureType();
        World world = event.getLocation().getWorld();
        ConfigurationHandler cfg = this.plugin.getGlobalStateManager();
        WorldConfigurationHandler wcfg = cfg.get(world);

        if (type == CreatureType.CHICKEN && (!wcfg.Chickens)) {
            event.setCancelled(true);
        } else if (type == CreatureType.COW && (!wcfg.Cows)) {
            event.setCancelled(true);
        } else if (type == CreatureType.PIG && (!wcfg.Pigs)) {
            event.setCancelled(true);
        } else if (type == CreatureType.SHEEP && (!wcfg.Sheeps)) {
            event.setCancelled(true);
        } else if (type == CreatureType.SQUID && (!wcfg.Squids)) {
            event.setCancelled(true);
        } else if (type == CreatureType.CREEPER && (!wcfg.Creepers)) {
            event.setCancelled(true);
        } else if (type == CreatureType.SKELETON && (!wcfg.Skeletons)) {
            event.setCancelled(true);
        } else if (type == CreatureType.SLIME && (!wcfg.Slimes)) {
            event.setCancelled(true);
        } else if (type == CreatureType.ZOMBIE && (!wcfg.Zombies)) {
            event.setCancelled(true);
        } else if (type == CreatureType.SPIDER && (!wcfg.Spiders)) {
            event.setCancelled(true);
        } else if (type == CreatureType.WOLF && (!wcfg.Wolves)) {
            event.setCancelled(true);
        } else if (type == CreatureType.GHAST && (!wcfg.Ghasts)) {
            event.setCancelled(true);
        } else if (type == CreatureType.PIG_ZOMBIE && (!wcfg.PigZombies)) {
            event.setCancelled(true);
        } else if (type == CreatureType.ENDERMAN && (!wcfg.Endermen)) {
            event.setCancelled(true);
        } else if (type == CreatureType.CAVE_SPIDER && (!wcfg.Cavespider)) {
            event.setCancelled(true);
        } else if (type == CreatureType.SILVERFISH && (!wcfg.Silverfish)) {
            event.setCancelled(true);
        }

    }
}