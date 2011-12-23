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

import java.util.HashMap;

public class Zones {

    private HashMap<String, ZoneData> zones;

    public void set(String name, int fx, int fy, int fz, int tx, int ty, int tz, World w) {
        if (!this.zones.containsKey(name)) {
            ZoneData zd = new ZoneData();
            zd.setName(name);
            zd.setType(new ZoneFlags());
            zd.setFromX(fx);
            zd.setFromY(fy);
            zd.setFromZ(fz);
            zd.setToX(tx);
            zd.setToY(ty);
            zd.setToZ(tz);
            zd.setWorld(w);
            this.zones.put(name, zd);
        }
    }

    public void remove(String name) {
        if (this.zones.containsKey(name)) {
            this.zones.remove(name);
        }
    }

    public HashMap<String, ZoneData> getZones() {
        return this.zones;
    }
}