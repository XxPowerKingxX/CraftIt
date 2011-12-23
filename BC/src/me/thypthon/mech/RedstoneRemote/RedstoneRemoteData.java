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

package me.thypthon.mech.RedstoneRemote;

import me.thypthon.serialize.SerializedLocation;
import org.bukkit.Material;

import java.io.Serializable;

@SuppressWarnings("serial")
public class RedstoneRemoteData implements Serializable {

    private byte data;
    private SerializedLocation loc;
    private Material mat;

    public void setData(byte data) {
        this.data = data;
    }

    public void setLocation(SerializedLocation loc) {
        this.loc = loc;
    }

    public void setMaterial(Material mat) {
        this.mat = mat;
    }

    public byte getData() {
        return this.data;
    }

    public SerializedLocation getLocation() {
        return this.loc;
    }

    public Material getMaterial() {
        return this.mat;
    }

}