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

package me.thypthon.handlers.zones;

import org.bukkit.World;

public class ZoneData {

    private String name;
    private ZoneFlags flags;
    private int fx;
    private int fy;
    private int fz;
    private int tx;
    private int ty;
    private int tz;
    private World world;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setType(ZoneFlags flags) {
        this.flags = flags;
    }

    public ZoneFlags getFlags() {
        return this.flags;
    }

    public void setFromX(int fx) {
        this.fx = fx;
    }

    public int getFromX() {
        return this.fx;
    }

    public void setFromY(int fy) {
        this.fy = fy;
    }

    public int getFromY() {
        return this.fy;
    }

    public void setFromZ(int fz) {
        this.fz = fz;
    }

    public int getFromZ() {
        return this.fz;
    }

    public void setToX(int tx) {
        this.tx = tx;
    }

    public int getToX() {
        return this.tx;
    }

    public void setToY(int ty) {
        this.ty = ty;
    }

    public int getToY() {
        return this.ty;
    }

    public void setToZ(int tz) {
        this.tz = tz;
    }

    public int getToZ() {
        return this.tz;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public World getWorld() {
        return this.world;
    }

}