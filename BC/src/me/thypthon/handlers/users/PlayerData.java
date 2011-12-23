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

package me.thypthon.handlers.users;

import me.thypthon.BC;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class PlayerData {

    @SuppressWarnings("unused")
	private BC plugin;

    public PlayerData(BC instance) {
        this.plugin = instance;
    }

    private Player player;
    private int status;
    private String group;
    private int gid;
    private int uid;
    private Location Wand1 = null;
    private Location Wand2 = null;
    private Location RedstoneLocation = null;
    Block targetblock = null;

    public void setTargetblock(Block b) {
        this.targetblock = b;
    }

    public Block getTargetblock() {
        return targetblock;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public void setRedstoneLocation(Location l) {
        this.RedstoneLocation = l;
    }

    public Location getRedstoneLocation() {
        return RedstoneLocation;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    
    public void setGroup(String string) {
        this.group = string;
    }
    
    public String getGroup() {
        return group;
    }

    public int getStatus() {
        return status;
    }

    public void setGID(int gid) {
        this.gid = gid;
    }

    public int getGID() {
        return gid;
    }

    public void setUID(int uid) {
        this.uid = uid;
    }

    public int getUID() {
        return uid;
    }

    public void setWand1(Location l) {
        this.Wand1 = l;
    }

    public Location getWand1() {
        return Wand1;
    }

    public void setWand2(Location l) {
        this.Wand2 = l;
    }

    public Location getWand2() {
        return Wand2;
    }

}
