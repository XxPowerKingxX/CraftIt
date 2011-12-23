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

package me.thypthon.serialize;

import me.thypthon.BC;
import org.bukkit.Location;

import java.io.Serializable;


public class SerializedLocation implements Serializable {

    private String world;
    private int x;
    private int y;
    private int z;
    private float yaw;
    private float pitch;

    private static final long serialVersionUID = 1L;

    public SerializedLocation(Location l) {
        this.world = l.getWorld().getName();
        this.x = l.getBlockX();
        this.y = l.getBlockY();
        this.z = l.getBlockZ();
        this.yaw = l.getYaw();
        this.pitch = l.getPitch();
    }

    public String getWorld() {
        return world;
    }

    public Location getLocation(BC plugin) {
        return new Location(plugin.getServer().getWorld(this.world), this.x, this.y, this.z, this.yaw, this.pitch);
    }

    public void setWorld(String world) {
        this.world = world;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public float getYaw() {
        return yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public float getPitch() {
        return pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

}