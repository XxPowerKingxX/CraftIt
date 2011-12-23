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

package me.thypthon.handlers.blocks;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import me.thypthon.BC;
import me.thypthon.handlers.users.UserHandler;

/**
 * Author: Xstasy
 * Date: 30.09.11
 * Time: 02:19
 */
public class WorldEditBridge {

    private WorldEditPlugin WEP;
    private BC plugin;
    @SuppressWarnings("unused")
	private UserHandler userHandler;
    @SuppressWarnings("unused")
	private BlockLog blockLog;
    @SuppressWarnings("unused")
	private BlockProtect blockProtect;

    public WorldEditBridge(BC instance) {
        this.plugin = instance;
        this.userHandler = instance.getUserHandler();
        this.blockLog = instance.getBlockLogHandler();
        this.blockProtect = instance.getBlockProtectHandler();
    }

    public void initialize() {
        this.WEP = (WorldEditPlugin) plugin.getServer().getPluginManager().getPlugin("WorldEdit");
    }

    public boolean isEnabled() {
        return (this.WEP != null);
    }

    public WorldEditPlugin getWEP() {
        return WEP;
    }
}