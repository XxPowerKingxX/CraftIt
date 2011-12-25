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

package me.thypthon.handlers.users;

import me.thypthon.BC;
import me.thypthon.handlers.utils.MySQLHandler;
//import me.thypthon.listeners.BCIRCListener;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.schwering.irc.lib.IRCConstants;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;

public class UserHandler {

    private HashMap<Player, PlayerData> users;
    private MySQLHandler sqlHandler;
    private PreparedStatement getUserPS;
    private PreparedStatement playerMatch;
    private PreparedStatement getUserFromUIDPS;
    private BC plugin;
    private Connection conn;
	@SuppressWarnings("unused")
	private PreparedStatement getUserPSFromId;
	private PreparedStatement getUserPSG;

    public UserHandler(BC instance) {
        this.plugin = instance;
        this.sqlHandler = instance.getSqlHandler();
        this.users = new HashMap<Player, PlayerData>();
    }

    public boolean initialize() {
        this.conn = this.sqlHandler.getConnection();
        try {
            this.getUserPS = this.conn.prepareStatement("SELECT * FROM users WHERE `name` = ?");
            this.getUserPSG = this.conn.prepareStatement("SELECT * FROM g_members WHERE `uname` = ?");
            this.getUserPSFromId = this.conn.prepareStatement("SELECT * FROM users WHERE `id` = ?");
            this.playerMatch = this.conn.prepareStatement("SELECT name FROM users WHERE `name` LIKE '%?%'");
            this.getUserFromUIDPS = this.conn.prepareStatement("SELECT name FROM users WHERE `id` = ?");
        } catch (SQLException e) {
            BC.log.log(Level.SEVERE, "[BC] Feil initialisering av prepared statements i UserHandler: ", e);
            return false;
        }
        return true;
    }

    public void login(Player p) {
        if (userExists(p)) {
            if (this.users.containsKey(p)) {
                this.users.remove(p);
            }
            this.users.put(p, getPlayerData(p));
        } else {
            if (register(p)) {
                this.users.put(p, getPlayerData(p));
            }
        }
        if(userInGroupExists(p) == false){
        	registertoGroups(p);
        }
    }

    public void logout(Player p) {
        this.users.remove(p);
    }

    public boolean register(Player p) {
        if (sqlHandler.update("REPLACE INTO `users` (`id`, `name`, `status`, `active`, `last_login`) VALUES (NULL, '" + p.getName() + "', 0, UNIX_TIMESTAMP(), UNIX_TIMESTAMP())")) {
            return true;
        } else {
            return false;
        }
    }

    public boolean register(String name) {
    	if (sqlHandler.update("REPLACE INTO `users` (`id`, `name`, `status`, `active`, `last_login`) VALUES (NULL, '" + name + "', 0, UNIX_TIMESTAMP(), UNIX_TIMESTAMP())")) {
            return true;
        } else {
            return false;
        }
    }
    
    public boolean registertoGroups(Player p) {
    	if (sqlHandler.update("INSERT IGNORE INTO `g_members` (`gid`, `uname`, `godkjent`) VALUES ('0', '" + p.getName() + "', 1)")) {
    		return true;
    	} else {
    		return false;
    	}
    }

    public boolean registertoGroups(String name) {
    	if (sqlHandler.update("INSERT IGNORE INTO `g_members` (`gid`, `uname`, `godkjent`) VALUES ('0', '" + name + "', 1)")) {
    		return true;
    	} else {
    		return false;
    	}
    }

    public PlayerData getCachedPlayerData(Player p) {
        return this.users.get(p);
    }

    public PlayerData getPlayerData(Player p) {
        PlayerData pd = new PlayerData(plugin);
        try {
            this.getUserPS.setString(1, p.getName());
            ResultSet rs = this.getUserPS.executeQuery();

            while (rs.next()) {
                pd.setUID(rs.getInt(1));
                pd.setStatus(rs.getInt(3));
                pd.setGroup(rs.getString(6));
                pd.setPlayer(p);
            }
        } catch (SQLException e) {
            BC.log.log(Level.SEVERE, "[BC] MySQL Error: " + Thread.currentThread().getStackTrace()[0].getMethodName(), e);
        }
        return pd;
    }

    public PlayerData getPlayerData(String name) {
        PlayerData pd = new PlayerData(plugin);
        try {
            this.getUserPS.setString(1, name);
            ResultSet rs = this.getUserPS.executeQuery();

            while (rs.next()) {
                pd.setUID(rs.getInt(1));
                pd.setStatus(rs.getInt(3));
                pd.setGroup(rs.getString(6));
            }
        } catch (SQLException e) {
            BC.log.log(Level.SEVERE, "[BC] MySQL Error: " + Thread.currentThread().getStackTrace()[0].getMethodName(), e);
        }
        return pd;
    }

    public String playerMatch(String name) {
        String player = null;
        try {
            this.playerMatch.setString(1, name);
            ResultSet rs = this.playerMatch.executeQuery();

            while(rs.next()) {
                player = rs.getString(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return player;
    }

    public void reloadUser(String name) {
        Player p = this.plugin.playerMatch(name);
        if (p != null) {
            this.users.remove(p);
            this.users.put(p, getPlayerData(p));
        }
    }

    public void reloadUser(Player p) {
        if (this.users.containsKey(p)) {
            this.users.remove(p);
        }
        this.users.put(p, getPlayerData(p));
    }

    public boolean userExists(Player p) {
        boolean exist = false;
        try {
            this.getUserPS.setString(1, p.getName());

            ResultSet rs = this.getUserPS.executeQuery();

            while (rs.next()) {
                exist = true;
            }
        } catch (SQLException e) {
            BC.log.log(Level.SEVERE, "[BC] MySQL Error: " + Thread.currentThread().getStackTrace()[0].getMethodName(), e);
        }
        return exist;
    }

    public boolean userExists(String name) {
        boolean exist = false;
        try {
            this.getUserPS.setString(1, name);

            ResultSet rs = this.getUserPS.executeQuery();

            while (rs.next()) {
                exist = true;
            }
        } catch (SQLException e) {
            BC.log.log(Level.SEVERE, "[BC] MySQL Error: " + Thread.currentThread().getStackTrace()[0].getMethodName(), e);
        }
        return exist;
    }
    
    public boolean userInGroupExists(Player p) {
        boolean exist = false;
        try {
            this.getUserPSG.setString(1, p.getName());

            ResultSet rs = this.getUserPSG.executeQuery();

            while (rs.next()) {
                exist = true;
            }
        } catch (SQLException e) {
            BC.log.log(Level.SEVERE, "[BC] MySQL Error: " + Thread.currentThread().getStackTrace()[0].getMethodName(), e);
        }
        return exist;
    }

    public boolean userInGroupExists(String name) {
        boolean exist = false;
        try {
            this.getUserPSG.setString(1, name);

            ResultSet rs = this.getUserPSG.executeQuery();

            while (rs.next()) {
                exist = true;
            }
        } catch (SQLException e) {
            BC.log.log(Level.SEVERE, "[BC] MySQL Error: " + Thread.currentThread().getStackTrace()[0].getMethodName(), e);
        }
        return exist;
    }

    public boolean setStatus(String name, int status) {
        if (sqlHandler.update("UPDATE `users` SET status='" + status + "' WHERE name='" + name + "'")) {
            return true;
        } else {
            return false;
        }
    }
    
    public boolean setGroup(String name, String gr, int godt) {
        if (sqlHandler.update("UPDATE `users` SET `group` = '" + gr + "' WHERE `name` = '" + name + "'") && sqlHandler.update("UPDATE `g_members` SET `gid` = '" + gr + "', `godkjent` = '" + godt +"' WHERE `uname` = '" + name + "'")) {
            return true;
        } else {
            return false;
        }
    }
    
    public boolean setGroup(Player p, String gr, int godt) {
        if (sqlHandler.update("UPDATE `users` SET `group` = '" + gr + "' WHERE `name` = '" + p.getName() + "'") && sqlHandler.update("UPDATE `g_members` SET `gid` = '" + gr + "', `godkjent` = '" + godt +"' WHERE `uname` = '" + p.getName() + "'")) {
            return true;
        } else {
            return false;
        }
    }
    
    public boolean setGroupGodt(Player p, int godt) {
        if (sqlHandler.update("UPDATE `g_members` SET `godkjent` = '" + godt +"' WHERE `uname` = '" + p.getName() + "'")) {
            return true;
        } else {
            return false;
        }
    }

    public int getUserStatus(Player p) {
        return this.users.get(p).getStatus();
    }

    public int getUserStatus(String name) {
        PlayerData pd = getPlayerData(name);
        return pd.getStatus();
    }
    
    public String getUserGroup(Player p) {
    	return this.users.get(p).getGroup();
    }
    
    public String getUserGroup(String name) {
        PlayerData pd = getPlayerData(name);
        return pd.getGroup();
    }
    
    public int getUID(Player p) {
        return this.users.get(p).getUID();
    }

    public String getNameFromUID(int uid) {
        String name = null;
        try {
            this.getUserFromUIDPS.setInt(1, uid);

            ResultSet rs = this.getUserFromUIDPS.executeQuery();

            while (rs.next()) {
                name = rs.getString(1);
            }
        } catch (SQLException e) {
            BC.log.log(Level.SEVERE, "[BC] MySQL Error: " + Thread.currentThread().getStackTrace()[0].getMethodName(), e);
        }
        return name;
    }

    public boolean canBuild(Player p) {

        if ((getUserStatus(p) < 1)) {
        	p.sendMessage(ChatColor.RED + "You do not have permission to build.");
            p.sendMessage(ChatColor.RED + "Go to craftit.no and send a permit.");
            return false;
        } else {
            return true;
        }
    }

    public String getNameColor(Player p) {
    	if (getUserStatus(p.getName()) == BC.ADMIN) {
            return BC.ADMIN_COLOR + "[Admin] " + p.getName() + ChatColor.WHITE;
        } else if (getUserStatus(p.getName()) == BC.MOD) {
            return BC.MOD_COLOR + "[Mod] " + p.getName() + ChatColor.WHITE;
        } else if (getUserStatus(p.getName()) == BC.BUILDER) {
            return BC.BUILDER_COLOR + p.getName() + ChatColor.WHITE;
        } else if (getUserStatus(p.getName()) == BC.GUEST) {
            return BC.GUEST_COLOR + "[Guest] " + p.getName() + ChatColor.WHITE;
        } else {
            return p.getName();
        }
    }

    public String getNameColor(String name) {
        if (getUserStatus(name) == BC.ADMIN) {
            return BC.ADMIN_COLOR + "[Admin] " + name + ChatColor.WHITE;
        } else if (getUserStatus(name) == BC.MOD) {
            return BC.MOD_COLOR + "[Mod] " + name + ChatColor.WHITE;
        } else if (getUserStatus(name) == BC.BUILDER) {
            return BC.BUILDER_COLOR + name + ChatColor.WHITE;
        } else if (getUserStatus(name) == BC.GUEST) {
            return BC.GUEST_COLOR + "[Guest] " + name + ChatColor.WHITE;
        } else {
            return name;
        }
    }
    
	public String getIRCNameColor(String name) {
    	if (getUserStatus(name) == BC.ADMIN) {
    		//BCIRCListener irc = plugin.getIRC();
            return IRCConstants.COLOR_INDICATOR + "7" + "[Admin]" + name + IRCConstants.COLOR_END_INDICATOR;
        } else if (getUserStatus(name) == BC.MOD) {
        	return IRCConstants.COLOR_INDICATOR + "2" + "[Mod]" + name + IRCConstants.COLOR_END_INDICATOR;
        } else if (getUserStatus(name) == BC.BUILDER) {
        	return name;
        } else if (getUserStatus(name) == BC.GUEST) {
        	return IRCConstants.COLOR_INDICATOR + "15" + "[Guest]" + name + IRCConstants.COLOR_END_INDICATOR;
        } else {
            return name;
        }
    }
}