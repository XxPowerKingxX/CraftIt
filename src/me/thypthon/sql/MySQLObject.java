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
import me.thypthon.handlers.utils.MySQLHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQLObject {

    private MySQLHandler sqlHandler;

    public MySQLObject(BC instance) {
        this.sqlHandler = instance.getSqlHandler();
    }

    public void replace(String name, int type, Object object) {
        try {
            Connection conn = this.sqlHandler.getConnection();

            PreparedStatement query = conn.prepareStatement("REPLACE INTO `objects` (`name`, `type`, `object`) VALUES(?, ?, ?)");

            query.setString(1, name);
            query.setInt(2, type);
            query.setObject(3, object);

            query.executeUpdate();
            query.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insert(String name, int type, Object object) {
        try {
            Connection conn = this.sqlHandler.getConnection();

            PreparedStatement query = conn.prepareStatement("INSERT INTO `objects` (`name`, `type`, `object`) VALUES(?, ?, ?)");

            query.setString(1, name);
            query.setInt(2, type);
            query.setObject(3, object);

            query.executeUpdate();

            query.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void delete(String name, int type) {

    }

    public Object get(String name, int type) {

        Object object = null;

        try {
            Connection conn = this.sqlHandler.getConnection();

            PreparedStatement query = conn.prepareStatement("SELECT object FROM `objects` WHERE name = ? AND type = ?");

            query.setString(1, name);
            query.setInt(2, type);

            ResultSet rs = query.executeQuery();

            if (rs.next()) {
                InputStream is = rs.getBlob(1).getBinaryStream();
                ObjectInputStream oip;
                oip = new ObjectInputStream(is);
                object = oip.readObject();
                oip.close();
                is.close();
            }

            query.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return object;
    }
}