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

/**
 * Author: Xstasy
 * Date: 30.09.11
 * Time: 04:23
 */
public enum APIMethod {
    BAN, BANK;

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}
