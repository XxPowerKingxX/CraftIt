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

package me.thypthon.sql;

import me.thypthon.BC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class sqlConnector {
    public ResultSet result;
    @SuppressWarnings("unused")
	private BC plugin;

    public sqlConnector(BC instance) {
        this.plugin = instance;
    }

    public synchronized Connection getConnection() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/minecraft", "root", "root");

            Logger.getLogger("Minecraft").log(Level.INFO, "[BC] Koblet til mysql databasen");
            return conn;
        } catch (SQLException e) {
            Logger.getLogger("Minecraft").log(Level.SEVERE, "[BC] Kunne ikke koble til databasen", e);
            return null;
        }
    }

    public Connection createConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection ret = DriverManager.getConnection("jdbc:mysql://localhost:3306/minecraft", "root", "root");
            return ret;

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}
