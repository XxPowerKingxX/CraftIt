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

package me.thypthon.handlers.blocks;

import me.thypthon.BC;
import me.thypthon.handlers.users.UserHandler;
import me.thypthon.handlers.utils.MySQLHandler;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;

public class BlockLog {

    private MySQLHandler sqlHandler;
    private UserHandler userHandler;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yy (HH:mm:ss)");

    private Connection conn;
    private PreparedStatement get;
    public PreparedStatement setlog;

    public BlockLog(BC instance) {
        this.sqlHandler = instance.getSqlHandler();
        this.userHandler = instance.getUserHandler();
    }

    public void initialize() {
        this.conn = this.sqlHandler.getConnection();
        try {
            this.setlog = this.conn.prepareStatement("INSERT INTO `blocklog` (`uid`, `x`, `y`, `z`, `action`, `world`, `data`, `time`) VALUES (?, ?, ?, ?, ?, ?, ?, UNIX_TIMESTAMP());");
            this.get = this.conn.prepareStatement("SELECT * FROM blocklog WHERE x=? AND y=? AND z=? AND world=? ORDER BY time ASC");
        } catch (SQLException e) {
            BC.log.log(Level.SEVERE, "[BC] Kunne ikke initialisere prepared statements for BlockLog.", e);
        }
    }

    public void exit() {
        try {
            this.setlog.close();
            this.get.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public boolean log(Player p, Block b, int action, String data) {
        try {
            this.setlog.setInt(1, this.userHandler.getUID(p));
            this.setlog.setShort(2, (short) b.getX());
            this.setlog.setShort(3, (short) b.getY());
            this.setlog.setShort(4, (short) b.getZ());
            this.setlog.setInt(5, action);
            this.setlog.setString(6, b.getWorld().getName());
            this.setlog.setString(7, data);

            this.setlog.executeUpdate();
            return true;
        } catch (SQLException e) {
            BC.log.log(Level.SEVERE, "[BC] Kunne ikke logge blokkendringen. ", e);
            return false;
        }
    }

    public boolean log(int uid, Block b, int action, String data) {
        try {
            this.setlog.setInt(1, uid);
            this.setlog.setShort(2, (short) b.getX());
            this.setlog.setShort(3, (short) b.getY());
            this.setlog.setShort(4, (short) b.getZ());
            this.setlog.setInt(5, action);
            this.setlog.setString(6, b.getWorld().getName());
            this.setlog.setString(7, data);

            this.setlog.executeUpdate();
            return true;
        } catch (SQLException e) {
            BC.log.log(Level.SEVERE, "[BC] Kunne ikke logge blokkendringen. ", e);
            return false;
        }
    }

    public ArrayList<String> getBlockLog(Block b) {

        ResultSet rs = null;
        ArrayList<String> row = new ArrayList<String>();

        try {
            this.get.setShort(1, (short) b.getX());
            this.get.setShort(2, (short) b.getY());
            this.get.setShort(3, (short) b.getZ());
            this.get.setString(4, b.getWorld().getName());
            this.get.execute();
            rs = this.get.getResultSet();


            while (rs.next()) {

                Date date = new Date(rs.getLong("time") * 1000);
                row.add("[" + dateFormat.format(date) + "]"
                        + this.userHandler.getNameFromUID(rs.getInt("uid"))
                        + ActiontoText(rs.getInt("action"), rs.getString("data")));
            }
            rs.close();

        } catch (SQLException e) {
            BC.log.log(Level.SEVERE, "[BC] SQL Exception i getBlockLog ", e);
        }
        return row;
    }

    public String ActiontoText(int action, String data) {
        if (action == 0) {
            return " removed " + data;
        } else if (action == 1) {
            return " placed " + data;
        } else if (action == 2) {
            return " admin sticked " + data;
        } else if (action == 3) {
            return " opend chest/furnance/dispenser/workbench";
        } else if (action == 4) {
            return " removed " + data + " from the chest";
        } else if (action == 5) {
            return " protected " + data;
        } else {
            return " unknown method";
        }
    }
}