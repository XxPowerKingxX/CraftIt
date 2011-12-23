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

package me.thypthon.handlers.config;

import me.thypthon.BC;
import org.bukkit.World;
import org.bukkit.util.config.Configuration;

import java.io.File;
import java.util.HashMap;
import java.util.Map;


@SuppressWarnings("deprecation")
public class ConfigurationHandler {

    private BC plugin;

    private Map<String, WorldConfigurationHandler> worlds;

    private Configuration config;
    private File configFile;

    public ConfigurationHandler(BC plugin) {
        this.plugin = plugin;
        this.worlds = new HashMap<String, WorldConfigurationHandler>();
    }

    // Config vars

    // Server stuff
    public boolean ispublic;

    // IRC
    public boolean irc;
    public String botnick;
    public String botserver;
    public int botport;
    public String botchannel;
    public String botnickservpass;
    public boolean botssl;

    // MySQL
    public String dbuser;
    public String dbpass;
    public String dbname;
    public String dbhost;
    public int dbport;

    // Dødsannonser \o/
    public boolean doedsannonser;

    // Fun stuff
    public boolean gold_boots;
    public int gold_boots_multiplier;
    public int gold_boots_access;

    public void load() {
        // Create the default configuration file
        configFile = new File(plugin.getDataFolder(), "config.yml");

        config = new Configuration(this.configFile);

        // Load configurations for each world
        for (World world : plugin.getServer().getWorlds()) {
            get(world);
        }

        loadConfiguration();

    }

	public void loadConfiguration() {

        config.load();

        // IRC
        botnick = config.getString("IRC.Nick", "CT");
        botnickservpass = config.getString("IRC.Nickserv-passord", "lardal9");
        botport = Integer.parseInt(config.getString("IRC.Port", "6667"));
        botserver = config.getString("IRC.Server", "irc.craftit.no");
        irc = config.getBoolean("IRC.Aktivert", false);
        botchannel = config.getString("IRC.Kanal", "#craftit");
        botssl = config.getBoolean("IRC.SSL", true);

        // Server-spesifikke instillinger
        ispublic = config.getBoolean("Server.Public", true);
        doedsannonser = config.getBoolean("Server.Doedsannonser", true);

        // Other crap
        gold_boots = config.getBoolean("Fun.Tools.Gullsko.Aktivert", true);
        gold_boots_multiplier = Integer.parseInt(config.getString("Fun.Tools.Gullsko.Multiplier", "2"));
        gold_boots_access = Integer.parseInt(config.getString("Fun.Tools.Gullsko.Tilgangsnivaa", "5"));

        config.save();
    }

    public void unload() {
        worlds.clear();
    }

    public WorldConfigurationHandler get(World world) {
        String worldName = world.getName();
        WorldConfigurationHandler config = worlds.get(worldName);

        if (config == null) {
            config = new WorldConfigurationHandler(plugin, worldName);
            worlds.put(worldName, config);
        }

        return config;
    }
}