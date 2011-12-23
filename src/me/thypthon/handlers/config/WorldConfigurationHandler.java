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
*/

package me.thypthon.handlers.config;

import me.thypthon.BC;
import org.bukkit.Location;
import org.bukkit.util.config.Configuration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("deprecation")
public class WorldConfigurationHandler {
    private BC plugin;

    private String worldName;
    @SuppressWarnings("unused")
	private Configuration parentConfig;
    private Configuration config;
    private File configFile;

    // Kommandoer
    public boolean BrukerCommand;
    public boolean KickCommand;
    public boolean CiCommand;
    public boolean StuckCommand;
    public boolean SpawnCommand;
    public boolean SetSpawnCommand;
    public boolean TpCommand;
    public boolean WhoCommand;
    public boolean TestCommand;
    public boolean CloakCommand;
    public boolean MessageCommand;
    public boolean ProtectCommand;
    public boolean BanCommand;

    // Skade
    public boolean Hunger;
    public boolean Damage;
    public boolean PVPDamage;
    public boolean CreatureDamage;
    public boolean DrowningDamage;
    public boolean FireDamage;
    public boolean LavaDamage;
    public boolean ContactDamage;
    public boolean TNTDamage;
    public boolean FallDamage;
    public boolean BlockDamage;
    public boolean VoidDamage;
    public boolean VoidTeleport;

    //Verden
    public boolean CreeperBlockDamage;
    public boolean TNTBlockDamage;
    public boolean TNTBlockDrops;
    public boolean IceRegen;
    public boolean SnowRegen;
    public boolean EndermenPickup;
    public boolean EndermenPickupProtected;
    public boolean alwaysDay;

    // Evigvarende verktøy.
    public List<Integer> InfinityTools;

    // Plugin
    public boolean BuildPermission;

    // Dyr
    public boolean Chickens;
    public boolean Pigs;
    public boolean Cows;
    public boolean Sheeps;
    public boolean Squids;
    public boolean Wolves;


    // Monstre
    public boolean Creepers;
    public boolean Skeletons;
    public boolean Slimes;
    public boolean Spiders;
    public boolean Zombies;
    public boolean PigZombies;
    public boolean Ghasts;
    public boolean Endermen;
    public boolean Cavespider;
    public boolean Silverfish;

    // Spawn
    public int sx;
    public int sy;
    public int sz;
    public double syaw;
    public double spitch;

    public WorldConfigurationHandler(BC plugin, String worldName) {
        File baseFolder = new File(plugin.getDataFolder(), "worlds/" + worldName);
        configFile = new File(baseFolder, "config.yml");

        this.plugin = plugin;
        this.worldName = worldName;
        this.parentConfig = plugin.getConfiguration();

        config = new Configuration(this.configFile);
        loadConfiguration();

        BC.log.info("[BC] Lastet konfigurasjon for: '" + worldName + '"');
    }

    public void setSpawn(Location l) {

        config.setProperty("Spawn.X", l.getBlockX());
        sx = l.getBlockX();
        config.setProperty("Spawn.Y", l.getBlockY());
        sy = l.getBlockY();
        config.setProperty("Spawn.Z", l.getBlockZ());
        sz = l.getBlockZ();

        config.setProperty("Spawn.Yaw", Double.parseDouble(Float.toString(l.getYaw())));
        syaw = Double.parseDouble(Float.toString(l.getYaw()));
        config.setProperty("Spawn.Pitch", Double.parseDouble(Float.toString(l.getPitch())));
        spitch = Double.parseDouble(Float.toString(l.getPitch()));

        config.save();

    }

    public Location getSpawn() {
        Location l = new Location(this.plugin.getServer().getWorld(this.worldName), sx, sy, sz, Float.parseFloat(Double.toString(syaw)), Float.parseFloat(Double.toString(spitch)));
        return l;
    }

    private void loadConfiguration() {
        config.load();

        // Kommandoer
        BrukerCommand = config.getBoolean("Kommandoer.Bruker", true);
        KickCommand = config.getBoolean("Kommandoer.Kick", true);
        CiCommand = config.getBoolean("Kommandoer.Ci", true);
        StuckCommand = config.getBoolean("Kommandoer.Stuck", true);
        SpawnCommand = config.getBoolean("Kommandoer.Spawn", true);
        SetSpawnCommand = config.getBoolean("Kommandoer.Setspawn", true);
        TpCommand = config.getBoolean("Kommandoer.Tp", true);
        WhoCommand = config.getBoolean("Kommandoer.Who", true);
        TestCommand = config.getBoolean("Kommandoer.Test", true);
        CloakCommand = config.getBoolean("Kommandoer.Cloak", true);
        MessageCommand = config.getBoolean("Kommandoer.Melding", true);
        ProtectCommand = config.getBoolean("Kommandoer.Protect", true);
        BanCommand = config.getBoolean("Kommandoer.Ban", true);

        // Bruker Skade
        Hunger = config.getBoolean("Bruker.Skade.Sulter", true);
        PVPDamage = config.getBoolean("Bruker.Skade.PVP-skade", true);
        Damage = config.getBoolean("Bruker.Skade.Skade", true);
        CreatureDamage = config.getBoolean("Bruker.Skade.Monster-mot-spiller-skade", true);
        DrowningDamage = config.getBoolean("Bruker.Skade.Miste-liv-ved-drukning", true);
        FireDamage = config.getBoolean("Bruker.Skade.Miste-liv-ved-brann", true);
        LavaDamage = config.getBoolean("Bruker.Skade.Miste-liv-i-lava", true);
        ContactDamage = config.getBoolean("Bruker.Skade.Miste-liv-ved-kontakt-med-blokk", true);
        TNTDamage = config.getBoolean("Bruker.Skade.Miste-liv-i-eksplosjon", true);
        FallDamage = config.getBoolean("Bruker.Skade.Miste-liv-ved-fall", true);
        BlockDamage = config.getBoolean("Bruker.Skade.Miste-liv-inni-blokk", true);
        VoidDamage = config.getBoolean("Bruker.Skade.Miste-liv-i-void", true);
        VoidTeleport = config.getBoolean("Bruker.Skade.Teleporter-ut-av-void", true);

        // Bruker Handlinger
        BuildPermission = true;

        // Verden
        CreeperBlockDamage = config.getBoolean("Verden.Blokker-blir-ekslodert-av-Creeper", true);
        TNTBlockDamage = config.getBoolean("Verden.Blokker-blir-eksplodert-av-TNT", true);
        TNTBlockDrops = config.getBoolean("Verden.Eksplosjoner-spawner-drops", true);
        SnowRegen = config.getBoolean("Verden.Snoe-regenereres-av-vaer", true);
        IceRegen = config.getBoolean("Verden.Is-regenereres-av-vaer", true);
        EndermenPickup = config.getBoolean("Verden.Endermen-plukker-opp-blokker", true);
        EndermenPickupProtected = config.getBoolean("Verden.Endermen-plukker-opp-beskyttede-blokker", false);
        alwaysDay = config.getBoolean("Verden.Alltid-dag", false);

        // Evigvarende verktøy
        ArrayList<Integer> infpreset = new ArrayList<Integer>();
        String infpresets = "269,273,256,277,284,270,274,257,278,285,271,275,258,279,286,290,291,292,293,294,259,346,359,268,272,267,283,276";
        for (String id : infpresets.split(",")) {
            infpreset.add(Integer.parseInt(id));
        }
        InfinityTools = config.getIntList("Verktoy.Evigvarende", infpreset);
        BC.log.info("[BC] Lastet " + InfinityTools.size() + " verktøy.");


        // Dyr
        Chickens = config.getBoolean("Dyr.Kyllinger", true);
        Pigs = config.getBoolean("Dyr.Griser", true);
        Cows = config.getBoolean("Dyr.Kuer", true);
        Sheeps = config.getBoolean("Dyr.Sauer", true);
        Squids = config.getBoolean("Dyr.Blekkspruter", true);
        Wolves = config.getBoolean("Dyr.Ulver", true);

        // Monstre
        Creepers = config.getBoolean("Monstre.Creepere", true);
        Skeletons = config.getBoolean("Monstre.Skeletons", true);
        Slimes = config.getBoolean("Monstre.Slimes", true);
        Spiders = config.getBoolean("Monstre.Edderkopper", true);
        Zombies = config.getBoolean("Monstre.Zombier", true);
        PigZombies = config.getBoolean("Monstre.Grisezombier", true);
        Ghasts = config.getBoolean("Monstre.Ghasts", true);
        Endermen = config.getBoolean("Monstre.Endermen", true);
        Cavespider = config.getBoolean("Monstre.hule-edderkopper", true);
        Silverfish = config.getBoolean("Monstre.Soelvkre", true);

        // Spawn
        sx = config.getInt("Spawn.X", this.plugin.getServer().getWorld(this.worldName).getSpawnLocation().getBlockX());
        sy = config.getInt("Spawn.Y", this.plugin.getServer().getWorld(this.worldName).getSpawnLocation().getBlockY());
        sz = config.getInt("Spawn.Z", this.plugin.getServer().getWorld(this.worldName).getSpawnLocation().getBlockZ());
        syaw = config.getDouble("Spawn.Yaw", Double.parseDouble(Float.toString(this.plugin.getServer().getWorld(this.worldName).getSpawnLocation().getYaw())));
        spitch = config.getDouble("Spawn.Pitch", Double.parseDouble(Float.toString(this.plugin.getServer().getWorld(this.worldName).getSpawnLocation().getPitch())));

        config.save();
    }

    public String getWorldName() {
        return this.worldName;
    }

}