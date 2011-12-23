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

package me.thypthon.handlers.utils;

import me.thypthon.BC;
import me.thypthon.sql.sqlConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;

public class MySQLHandler {

    private Connection sqlConnection = null;
    private sqlConnector sqlConnector;

    public MySQLHandler(BC instance) {
        this.sqlConnector = instance.getSqlConnector();
    }

    public boolean initialize() {
        if (this.sqlConnection == null) {
            this.sqlConnection = this.sqlConnector.getConnection();
            if (this.sqlConnection == null) {
                BC.log.log(Level.SEVERE, "[BC] Feil ved tilkobling til databasen.");
            }
        }

        return true;
    }

    public Connection getConnection() {
        if (this.sqlConnection == null) {
            BC.log.log(Level.INFO,
                    "[BC] Tilkoblingen returnerte null, oppretter ny kobling.");
            initialize();
        }
        return this.sqlConnection;
    }

    public void closeConnection() {
        try {
            this.sqlConnection.close();
        } catch (SQLException e) {
            BC.log.log(Level.SEVERE,
                    "[BC] Kunne ikke lukke tilkoblingen til databasen.", e);
            e.printStackTrace();
        }
    }

    public void checkWarnings() {
        try {
            SQLWarning warning = this.sqlConnection.getWarnings();
            while (warning != null) {
                BC.log
                        .log(Level.WARNING,
                                "[BC] SQL-Advarsel: ", warning);
                warning = warning.getNextWarning();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            BC.log.log(Level.SEVERE, "[BC] Fikk ikke hentet warnings fra databasetilkobling! ", e);
        }
    }


    public boolean update(String query, Object[] array) {
        Connection conn = null;
        PreparedStatement ps = null;
        int counter = 1;
        long time = System.currentTimeMillis();
        if (array != null) {
            try {
                // conn = sqlConnector.getConnection();
                conn = this.sqlConnection;
                ps = conn.prepareStatement(query);

                for (Object o : array) {
                    if (o instanceof Integer) {
                        ps.setInt(counter, (Integer) o);
                    } else if (o instanceof String) {
                        ps.setString(counter, (String) o);
                    } else {
                        BC.log.log(Level.SEVERE,
                                "[BC] Ukjent objekt i mysql-handler.(" + o.getClass().toString() + ")");
                        BC.log.log(Level.SEVERE, Arrays.toString(array));
                        return false;
                    }
                    counter++; // �ker indeks
                }
                array = null;
                ps.setEscapeProcessing(true);
                ps.executeUpdate();
                ps.close();
                // conn.close();

                long newTime = System.currentTimeMillis();
                if (newTime - time > 30) {
                    BC.log.log(Level.INFO,
                            "[BC] En spørring tok veldig lang tid( "
                                    + (newTime - time)
                                    + " ms). Spørringen var: " + query);
                }
                checkWarnings();
                ;
                return true;
            } catch (SQLException ex) {
                BC.log.log(Level.SEVERE, "[BC] SQL Exception", ex);
                return false;
            }
        } else { // Arrayet er tomt, ergo har man brukt funksjonen feil
            BC.log.log(Level.SEVERE,
                    "[BC] Empty array in mysqlhandler.update");
            return false;
        }
    }

    public boolean update(String query) {
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = this.sqlConnection;
            stmt = conn.createStatement();
            stmt.executeUpdate(query);
            stmt.close();
            checkWarnings();
            return true;
        } catch (SQLException ex) {
            BC.log.log(Level.SEVERE, "[BC] SQL Exception", ex);
            return false;
        }
    }

    public int insert(String query) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int id = -1;
        try {
            conn = this.sqlConnection;
            ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setEscapeProcessing(true);
            ps.executeUpdate();
            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt(1);
            }
            rs.close();
            ps.close();
            checkWarnings();
            return id;
        } catch (SQLException ex) {
            BC.log.log(Level.SEVERE, "[BC] SQL Exception", ex);
            return 0;
        }
    }

    public String getColumn(String query, String c) {
        Connection conn = null;
        Statement ps = null;
        ResultSet rs = null;
        String column = "";
        try {
            conn = this.sqlConnection;
            ps = conn.createStatement();
            rs = ps.executeQuery(query);

            if (rs.next()) {
                column = rs.getString(c);
            }

            rs.close();
            ps.close();
            checkWarnings();
            return column;
        } catch (SQLException ex) {
            BC.log.log(Level.SEVERE, "[BC] SQL Exception", ex);
            return null;
        }
    }

    public String getColumn(String query, String c, Object[] array) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String column = "";
        int counter = 1;
        if (array != null) {
            try {
                conn = this.sqlConnection;
                ps = conn.prepareStatement(query);

                // Her kjører man inn variablene
                for (Object o : array) {
                    if (o instanceof Integer) {
                        ps.setInt(counter, (Integer) o); // indeks og object som
                        // blir castet
                    } else if (o instanceof String) {
                        ps.setString(counter, (String) o);
                    } else {
                        BC.log
                                .log(Level.SEVERE,
                                        "[BC] Nullobjekt i mysql-handler. (getColumn)");
                        return null;
                    }
                    counter++;
                }
                array = null;
                rs = ps.executeQuery();

                if (rs.next()) {
                    column = rs.getString(c);
                }
                rs.close();
                ps.close();
                checkWarnings();
            } catch (SQLException ex) {
                BC.log.log(Level.SEVERE, "[BC] SQL Exception", ex);
                return null;
            }
        }
        return column;
    }

    public ArrayList<ArrayList<String>> getRows(String query, Object[] array) {
        ArrayList<ArrayList<String>> rows = new ArrayList<ArrayList<String>>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int counter = 1;
        int rowCounter = 0;

        if (array != null) {
            try {
                // conn = sqlConnector.getConnection();
                conn = this.sqlConnection;
                ps = conn.prepareStatement(query);

                // Her kjører man inn variablene
                for (Object o : array) {
                    if (o instanceof Integer) {
                        ps.setInt(counter, (Integer) o); // indeks og object som
                        // blir castet
                    } else if (o instanceof String) {
                        ps.setString(counter, (String) o);
                    } else {
                        BC.log
                                .log(Level.SEVERE,
                                        "[BC] Nullobjekt i mysql-handler. (getRows)");
                        return null;
                    }
                    counter++;
                }

                rs = ps.executeQuery();

                if (rs.next()) {
                    rows.add(new ArrayList<String>());
                    for (int i = 1; i <= array.length; i++) {
                        rows.get(rowCounter).add(rs.getString(i));
                    }
                    rowCounter++;
                }

                array = null;
                rs.close();
                ps.close();
                checkWarnings();
                // conn.close();
            } catch (SQLException ex) {
                BC.log.log(Level.SEVERE, "[BC] SQL Exception", ex);
                return null;
            }
        } else {
            BC.log.log(Level.SEVERE, Thread.currentThread()
                    .getStackTrace()[0].getMethodName() + " - array is null");
            return null;
        }
        return rows;
    }

    /**
* Checks if the string contains any non-alphabet characters or _
*
* @param string The string to check.
* @return true if it doesn't any non-alphabet characters or _, false if it does.
*/
    public static boolean checkString(String string) {

        for (int i = 0; i < string.length(); i++) {
            if (!Character.isLetterOrDigit(string.codePointAt(i))) {
                if (string.charAt(i) != '_') {
                    return false;
                }
            }
        }

        return true;
    }

}
