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

package me.thypthon.handlers.utils;

import java.util.ArrayList;
import java.util.logging.Filter;
import java.util.logging.LogRecord;

public class Console implements Filter {

    private ArrayList<String> remove;

    public Console() {
        remove = new ArrayList<String>();

        // Filtrer disse
        remove.add("Can't keep up! Did the system time change, or is the server overloaded?");
        remove.add("Nag author: 'Xstasy' of 'Insane' about the following: This plugin is not properly shutting down its async tasks when it is being reloaded.  This may cause conflicts with the newly loaded version of the plugin");
    }

    public boolean isLoggable(LogRecord logRecord) {
        if (!remove.contains(logRecord.getMessage())) {
            return true;
        } else {
            return false;
        }
    }

}