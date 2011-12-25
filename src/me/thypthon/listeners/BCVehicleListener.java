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

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;
import org.bukkit.entity.Vehicle;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.bukkit.event.vehicle.VehicleListener;
import org.bukkit.event.vehicle.VehicleUpdateEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.util.Vector;

public class BCVehicleListener extends VehicleListener {

	@SuppressWarnings("unused")
    private BC plugin;

    public BCVehicleListener(BC instance) {
        this.plugin = instance;
    }

    // Konstant hastighet for minecarts.
    public void onVehicleUpdate(VehicleUpdateEvent e) {
        Vehicle v = e.getVehicle();
        Location l = e.getVehicle().getLocation();
         Block b = e.getVehicle().getWorld().getBlockAt((int)Math.floor(l.getX()), (int)Math.floor(l.getY()) - 1, (int)Math.floor(l.getZ()));
         
         if(b.getType() == Material.DIAMOND_BLOCK){
        	 v.remove();
         }
    	
        if (!(v instanceof Minecart) || v.getPassenger() == null) {   	
            return;
        }

        Vector vel = v.getVelocity();
        if (vel.getX() > 0) {
            vel.setX(0.6);
        }
        if (vel.getX() < 0) {
            vel.setX(-0.6);
        }
        if (vel.getZ() > 0) {
            vel.setZ(0.6);
        }
        if (vel.getZ() < 0) {
            vel.setZ(-0.6);
        }
        v.setVelocity(vel);
    }

	public void onVehicleExit(VehicleExitEvent e){
    	Vehicle v = e.getVehicle();
    	v.remove();
    	Player player = (Player) e.getExited();
        PlayerInventory inventory = player.getInventory(); // The player's inventory
        ItemStack diamondstack = new ItemStack(Material.MINECART, 0); // A stack of diamonds
        inventory.addItem(diamondstack); // Adds a stack of diamonds to the player's inventory
    }
}