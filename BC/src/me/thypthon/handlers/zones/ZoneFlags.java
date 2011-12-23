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

public class ZoneFlags {

    private String name;
    private boolean PVP = true;
    private boolean Mobs = true;
    private boolean Animals = true;
    private boolean Hunger = true;
    private boolean Sprint = true;
    private boolean Critical = true;
    private boolean Spleef = true;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setPVP(boolean b) {
        this.PVP = b;
    }

    public boolean getPVP() {
        return this.PVP;
    }

    public void setMobs(boolean b) {
        this.Mobs = b;
    }

    public boolean getMobs() {
        return this.Mobs;
    }

    public void setAnimals(boolean b) {
        this.Animals = b;
    }

    public boolean getAnimals() {
        return this.Animals;
    }

    public void setHunger(boolean b) {
        this.Hunger = b;
    }

    public boolean getHunger() {
        return this.Hunger;
    }

    public void setCritical(boolean b) {
        this.Critical = b;
    }

    public boolean getCritical() {
        return this.Critical;
    }

    public void setSpleef(boolean b) {
        this.Spleef = b;
    }

    public boolean getSpleef() {
        return this.Spleef;
    }

    public void setSprint(boolean b) {
        this.Sprint = b;
    }

    public boolean getSprint() {
        return this.Sprint;
    }

}