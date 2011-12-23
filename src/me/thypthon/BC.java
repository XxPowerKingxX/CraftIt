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

package me.thypthon;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import me.thypthon.commands.*;
import me.thypthon.handlers.blocks.BlockLog;
import me.thypthon.handlers.blocks.BlockProtect;
import me.thypthon.handlers.blocks.LogHandler;
import me.thypthon.handlers.blocks.WorldEditBridge;
import me.thypthon.handlers.config.ConfigurationHandler;
import me.thypthon.handlers.groups.GroupHandler;
import me.thypthon.handlers.users.PlayerData;
import me.thypthon.handlers.users.UserHandler;
import me.thypthon.handlers.utils.Console;
import me.thypthon.handlers.utils.MySQLHandler;
import me.thypthon.handlers.warns.WarningsHandler;
import me.thypthon.handlers.zones.Zones;
import me.thypthon.listeners.*;
import me.thypthon.mech.RedstoneRemote.RedstoneRemote;
import me.thypthon.sql.MySQLObject;
import me.thypthon.sql.sqlConnector;
import me.thypthon.timers.FiveSecTimer;
import me.thypthon.timers.OneSecTimer;
import me.thypthon.timers.TenSecTimer;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
 
public class BC extends JavaPlugin {

    // Statuser og farger
    public static final int ADMIN = 10;
    public static final ChatColor ADMIN_COLOR = ChatColor.GOLD;
    public static final int MOD = 5;
    public static final ChatColor MOD_COLOR = ChatColor.BLUE;
    public static final int BUILDER = 1;
    public static final ChatColor BUILDER_COLOR = ChatColor.WHITE;
    public static final int GUEST = 0;
    public static final ChatColor GUEST_COLOR = ChatColor.GRAY;
    
    public static BC instance;
    public static boolean enableSpout;
    // Configuration
    public final ConfigurationHandler configuration;


    public BC() {
        super();
        configuration = new ConfigurationHandler(this);
    }
    
    // Statiske Faux Brukere
    public static final int CREEPER_UID = 999990;
    public static final int ENDERMEN_UID = 999991;
    public static final int SERVER_UID = 999999;

    // SQL
    private sqlConnector sqlConnector = new sqlConnector(this);
    private MySQLHandler sqlHandler = new MySQLHandler(this);
    private MySQLObject mysqlobject = new MySQLObject(this);

    // Handlers
    private UserHandler userHandler = new UserHandler(this);
    private WarningsHandler warningsHandler = new WarningsHandler(this);
    private GroupHandler groupHandler = new GroupHandler(this);
    private BlockLog blocklog = new BlockLog(this);
    private BlockProtect blockProtect = new BlockProtect(this);
    private RedstoneRemote rrh = new RedstoneRemote(this);
    private Zones zones = new Zones();

    // WorldEdit Bridging
    @SuppressWarnings("unused")
	private WorldEditBridge worldEditBridge = new WorldEditBridge(this);

    // Logger
    public static final Logger log = Logger.getLogger("Minecraft");
    private LogHandler logHandler = new LogHandler();
    
    // Listeners
    public BCEntityListener entityListener = new BCEntityListener(this);
    public BCBlockListener blockListener = new BCBlockListener(this);
    public BCPlayerListener playerListener = new BCPlayerListener(this);
    public BCVehicleListener vehicleListener = new BCVehicleListener(this);
    public BCIRCListener irc = new BCIRCListener(this);

    // Timere
    private Runnable ost = new OneSecTimer(this);
    private Runnable fst = new FiveSecTimer(this);
    private Runnable tst = new TenSecTimer(this);

    public void onDisable() {
        this.rrh.exit();
        this.blockProtect.exit();
        this.blocklog.exit();
        
        @SuppressWarnings("unused")
		ConfigurationHandler cfg = getGlobalStateManager();
        if(cfg.irc) {
            this.irc.exit();
        }
        
        Bukkit.getServer().getScheduler().cancelTasks(this);
        log.log(Level.INFO, "[BC] Plugin stoppet.");
    }

    public void onEnable() {
        BC.instance = this;
        Plugin checkplugin = this.getServer().getPluginManager().getPlugin("JSONAPI");
        if (checkplugin != null) {
            log.info("[BC] JsonAPI er lastet.");
        } else {
            log.info("[BC] JsonAPI er ikke lastet.");
        }
        checkplugin = null;
        checkplugin = this.getServer().getPluginManager().getPlugin("Spout");
        if (checkplugin != null) {
            enableSpout = true;
            log.info("[BC] Spout-funksjonalitet aktivert.");
        } else {
            log.info("[BC] Spout-funksjonalitet ikke tilgjengelig.");
        }

        // Log filter
        Logger.getLogger("Minecraft").setFilter(new Console());

        configuration.load();

        registerEvents();

        sqlConnection();
        this.rrh.initialize();

        // Opprett tabeller som ikke finnes.
        this.sqlHandler.update("CREATE TABLE IF NOT EXISTS `blocklog` (`uid` int(11) NOT NULL,`x` smallint(6) NOT NULL,`y` smallint(6) NOT NULL,`z` smallint(6) NOT NULL,`action` tinyint(1) NOT NULL,`world` varchar(50) NOT NULL,`data` varchar(255) NOT NULL,`time` int(11) NOT NULL) ENGINE=InnoDB DEFAULT CHARSET=latin1");
        this.sqlHandler.update("CREATE TABLE IF NOT EXISTS `blocks` ( `uid` int(11) NOT NULL, `x` int(6) NOT NULL, `y` int(6) NOT NULL, `z` int(6) NOT NULL, `world` varchar(50) NOT NULL, UNIQUE KEY `location` (`x`,`y`,`z`,`world`)) ENGINE=InnoDB DEFAULT CHARSET=latin1");
        this.sqlHandler.update("CREATE TABLE IF NOT EXISTS `log` (`id` int(11) NOT NULL AUTO_INCREMENT,`pid` int(6) NOT NULL,`vid` int(6) NOT NULL,`aid` int(3) NOT NULL,`amount` int(11) NOT NULL DEFAULT '0',`data` varchar(255) NOT NULL,`time` int(11) NOT NULL,PRIMARY KEY (`id`)) ENGINE=MyISAM DEFAULT CHARSET=latin1");
        this.sqlHandler.update("CREATE TABLE IF NOT EXISTS `users` (`id` int(6) NOT NULL AUTO_INCREMENT,`name` varchar(16) NOT NULL,`status` int(2) NOT NULL DEFAULT '0',`active` int(11) NOT NULL,`last_login` int(11) NOT NULL,PRIMARY KEY (`id`),UNIQUE KEY `name` (`name`)) ENGINE=MyISAM AUTO_INCREMENT=51 DEFAULT CHARSET=latin1");
        this.sqlHandler.update("CREATE TABLE IF NOT EXISTS `objects` (`id` int(11) NOT NULL AUTO_INCREMENT, `name` varchar(255) NOT NULL, `type` int(3) NOT NULL, `object` blob NOT NULL, PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;");
        this.sqlHandler.update("CREATE TABLE IF NOT EXISTS `group` ( `id` int(11) NOT NULL AUTO_INCREMENT, `name` varchar(255) NOT NULL, `uname` varchar(255) NOT NULL, `eier` varchar(255) NOT NULL, PRIMARY KEY (`id`) ) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;");
        this.sqlHandler.update("CREATE TABLE IF NOT EXISTS `warns` (`wid` int(11) NOT NULL,`uid` int(11) NOT NULL,`by` int(11) NOT NULL,`reason` mediumtext NOT NULL,`date` varchar(255) NOT NULL,`pos` varchar(255) NOT NULL,PRIMARY KEY (`wid`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;");
        this.sqlHandler.update("INSERT IGNORE INTO `users` SET id='999990', name='Creeper', status='1', active = UNIX_TIMESTAMP(), last_login = UNIX_TIMESTAMP()");
        this.sqlHandler.update("INSERT IGNORE INTO `users` SET id='999991', name='Endermen', status='1', active = UNIX_TIMESTAMP(), last_login = UNIX_TIMESTAMP()");
        this.sqlHandler.update("INSERT IGNORE INTO `users` SET id='999999', name='Server', status='1', active = UNIX_TIMESTAMP(), last_login = UNIX_TIMESTAMP()");
        this.userHandler.initialize();
        this.blockProtect.initialize();
        this.blocklog.initialize();
        this.irc.initialize();
        registerCommands();

        for (Player p : getServer().getOnlinePlayers()) {
            this.userHandler.login(p);
        }

        // Timere
        Bukkit.getServer().getScheduler().scheduleAsyncRepeatingTask(this, ost, 0, 20);
        Bukkit.getServer().getScheduler().scheduleAsyncRepeatingTask(this, fst, 0, 100);
        Bukkit.getServer().getScheduler().scheduleAsyncRepeatingTask(this, tst, 0, 200);

        log.log(Level.INFO, "[BC] Plugin lastet.");

    }

    public void registerEvents() {

        PluginManager pm = getServer().getPluginManager();

        // Block Events
        pm.registerEvent(Event.Type.BLOCK_BREAK, this.blockListener, Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.BLOCK_DAMAGE, this.blockListener, Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.BLOCK_IGNITE, this.blockListener, Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.BLOCK_PLACE, this.blockListener, Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.BLOCK_PHYSICS, this.blockListener, Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.BLOCK_FROMTO, this.blockListener, Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.BLOCK_BURN, this.blockListener, Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.BLOCK_FORM, this.blockListener, Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.REDSTONE_CHANGE, this.blockListener, Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.BLOCK_PISTON_EXTEND, this.blockListener, Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.BLOCK_PISTON_RETRACT, this.blockListener, Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.SIGN_CHANGE, this.blockListener, Event.Priority.Normal, this);

        // Player Events
        pm.registerEvent(Event.Type.PLAYER_LOGIN, this.playerListener, Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.PLAYER_JOIN, this.playerListener, Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.PLAYER_MOVE, this.playerListener, Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.PLAYER_QUIT, this.playerListener, Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.PLAYER_INTERACT, this.playerListener, Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.PLAYER_CHAT, this.playerListener, Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.PLAYER_DROP_ITEM, this.playerListener, Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.PLAYER_PICKUP_ITEM, this.playerListener, Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.PLAYER_KICK, this.playerListener, Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.PLAYER_INTERACT_ENTITY, this.playerListener, Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.PLAYER_BUCKET_EMPTY, this.playerListener, Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.PLAYER_BUCKET_FILL, this.playerListener, Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.PLAYER_ANIMATION, this.playerListener, Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.PLAYER_BED_ENTER, this.playerListener, Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.PLAYER_BED_LEAVE, this.playerListener, Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.PLAYER_EGG_THROW, this.playerListener, Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.PLAYER_ITEM_HELD, this.playerListener, Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.PLAYER_PORTAL, this.playerListener, Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.PLAYER_PRELOGIN, this.playerListener, Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.PLAYER_RESPAWN, this.playerListener, Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.PLAYER_TELEPORT, this.playerListener, Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.PLAYER_TOGGLE_SNEAK, this.playerListener, Event.Priority.Normal, this);
        
        // Entity Events
        pm.registerEvent(Event.Type.CREATURE_SPAWN, this.entityListener, Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.ENTITY_COMBUST, this.entityListener, Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.ENTITY_DAMAGE, this.entityListener, Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.ENTITY_DEATH, this.entityListener, Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.ENTITY_EXPLODE, this.entityListener, Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.ENTITY_INTERACT, this.entityListener, Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.ENTITY_TARGET, this.entityListener, Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.ENDERMAN_PICKUP, this.entityListener, Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.ENDERMAN_PLACE, this.entityListener, Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.PIG_ZAP, this.entityListener, Event.Priority.Normal, this);

        // Vehicle Events
        pm.registerEvent(Event.Type.VEHICLE_UPDATE, this.vehicleListener, Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.VEHICLE_EXIT, this.vehicleListener, Event.Priority.High, this);
        pm.registerEvent(Event.Type.VEHICLE_CREATE, this.vehicleListener, Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.VEHICLE_ENTER, this.vehicleListener, Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.VEHICLE_MOVE, this.vehicleListener, Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.VEHICLE_DAMAGE, this.vehicleListener, Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.VEHICLE_COLLISION_BLOCK, this.vehicleListener, Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.VEHICLE_COLLISION_ENTITY, this.vehicleListener, Event.Priority.Normal, this);

    }

    public void registerCommands() {
        getCommand("kick").setExecutor(new KickCommand(this));
        getCommand("add").setExecutor(new AddUserCommand(this));
        getCommand("a").setExecutor(new AdminChatCommand(this));
        getCommand("admin").setExecutor(new AdminUserCommand(this));
        getCommand("demote").setExecutor(new DemoteCommand(this));
        getCommand("mod").setExecutor(new ModUserCommand(this));
        getCommand("stuck").setExecutor(new StuckCommand(this));
        //getCommand("gr").setExecutor(new GroupCommand(this));
        getCommand("lw").setExecutor(new lwCommand(this));
        getCommand("warn").setExecutor(new warnCommand(this));

    }

    public void sqlConnection() {
        Connection conn = sqlConnector.createConnection();

        if (conn == null) {
            log.log(Level.SEVERE, "[BC] Kunne ikke opprette forbindelse til mysql, disabler plugin.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        } else {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
                log.log(Level.SEVERE, "[BC] Feil under lukking av mysql tilkobling.", e);
            }
        }
        sqlHandler.initialize();
    }

    public MySQLHandler getSqlHandler() {
        return sqlHandler;
    }

    public sqlConnector getSqlConnector() {
        return sqlConnector;
    }

    public LogHandler getLogHandler() {
        return logHandler;
    }

    public UserHandler getUserHandler() {
        return userHandler;
    }
    
    public GroupHandler getGroupHandler() {
		return groupHandler;
    }

    public BlockProtect getBlockProtectHandler() {
        return blockProtect;
    }

    public BlockLog getBlockLogHandler() {
        return blocklog;
    }

    public MySQLObject getMySQLObject() {
        return mysqlobject;
    }
    
    public BCIRCListener getIRC() {
        return this.irc;
    }

    public RedstoneRemote getRedstoneRemote() {
        return rrh;
    }

    public Zones getZones() {
        return zones;
    }

    public static String getPlayerIP(Player player) {
        String address = player.getAddress().toString();
        address = address.substring(1);
        address = address.split(":")[0];
        return address;
    }

    public static void createDefaultConfiguration(File actual, String defaultName) {

        // Make parent directories
        File parent = actual.getParentFile();
        if (!parent.exists()) {
            parent.mkdirs();
        }

        if (actual.exists()) {
            return;
        }

    }

    public ConfigurationHandler getGlobalStateManager() {
        return configuration;
    }

    public Player playerMatch(String name) {
        if (this.getServer().getOnlinePlayers().length < 1) {
            return null;
        }

        Player[] online = this.getServer().getOnlinePlayers();
        Player lastPlayer = null;

        for (Player player : online) {
            String playerName = player.getName();
            String playerDisplayName = player.getDisplayName();

            if (playerName.equalsIgnoreCase(name)) {
                lastPlayer = player;
                break;
            } else if (playerDisplayName.equalsIgnoreCase(name)) {
                lastPlayer = player;
                break;
            }

            if (playerName.toLowerCase().indexOf(name.toLowerCase()) != -1) {
                if (lastPlayer != null) {
                    return null;
                }

                lastPlayer = player;
            } else if (playerDisplayName.toLowerCase().indexOf(
                    name.toLowerCase()) != -1) {
                if (lastPlayer != null) {
                    return null;
                }

                lastPlayer = player;
            }
        }

        return lastPlayer;
    }

    public int getUserStatus(String name) {
        PlayerData pd = this.userHandler.getPlayerData(name);
        return pd.getStatus();
    }

    public boolean registerUser(String name) {
        return this.userHandler.register(name);
    }

    public boolean isPublic() {
        return configuration.ispublic;
    }

    public boolean setUserStatus(String name, int status) {
        if (this.userHandler.userExists(name)) {
            if (this.userHandler.setStatus(name, status)) {
                this.userHandler.reloadUser(name);
                return true;
            } else {
                return false;
            }
        } else {
            this.userHandler.register(name);
            if (this.userHandler.setStatus(name, status)) {
                this.userHandler.reloadUser(name);
                return true;
            } else {
                return false;
            }
        }
    }

    public void broadcastAll(String message) {
        for (World w : this.getServer().getWorlds()) {
            for (Player p : w.getPlayers()) {
                p.sendMessage(message);
            }
        }
    }
    
    public void broadcastAdminChat(String message){
    	for (World w : this.getServer().getWorlds()) {
            for (Player p : w.getPlayers()) {
            	if(this.userHandler.getUserStatus(p) == 5 || this.userHandler.getUserStatus(p) == 10){
            		p.sendMessage("(§6Admin§f/§9Mod§f) " + message);
            	}
            }
        }
    }

	public WarningsHandler getWarningsHandler() {
		// TODO Auto-generated method stub
		return warningsHandler;
	}
}