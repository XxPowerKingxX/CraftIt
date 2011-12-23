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

package me.thypthon.mech.RedstoneRemote;

import me.thypthon.BC;
import me.thypthon.handlers.utils.MySQLHandler;
import me.thypthon.serialize.SerializedLocation;
import me.thypthon.sql.MySQLObject;
import me.thypthon.sql.MySQLObjectType;
import org.bukkit.block.Block;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

@SuppressWarnings("serial")
public class RedstoneRemote implements Serializable {

    private MySQLObject mysqlobject;
    @SuppressWarnings("unused")
	private MySQLHandler mysqlhandler;
    private HashMap<String, ArrayList<RedstoneRemoteData>> recievers;

    public RedstoneRemote(BC instance) {
        this.mysqlhandler = instance.getSqlHandler();
        this.mysqlobject = instance.getMySQLObject();
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
	public void initialize() {
        Object o = this.mysqlobject.get("redstoneremote", MySQLObjectType.REDSTONE_REMOTE);
        if (o != null && o instanceof HashMap) {
            this.recievers = (HashMap) o;
        } else {
            this.recievers = new HashMap<String, ArrayList<RedstoneRemoteData>>();
        }
    }

    public void exit() {
        this.mysqlobject.replace("redstoneremote", MySQLObjectType.REDSTONE_REMOTE, this.recievers);
    }

    @SuppressWarnings({"rawtypes"})
    public void setReciever(Block b, String name) {

        byte face = b.getData();

        RedstoneRemoteData rrd = new RedstoneRemoteData();

        rrd.setData(face);
        SerializedLocation sl = new SerializedLocation(b.getLocation());
        rrd.setLocation(sl);
        rrd.setMaterial(b.getType());

        if (this.recievers.containsKey(name)) {
            ArrayList<RedstoneRemoteData> recvs = new ArrayList<RedstoneRemoteData>();
            recvs = this.recievers.get(name);
            Iterator itr = recvs.iterator();
            boolean add = true;
            while (itr.hasNext()) {
                RedstoneRemoteData irrd = (RedstoneRemoteData) itr.next();
                if (rrd.getLocation() == irrd.getLocation()) {
                    add = false;
                }
            }
            if (add) {
                recvs.add(rrd);
                this.recievers.put(name, recvs);
                BC.log.info("La til ny reciever på eksisterende frekvens");
            }

        } else {
            ArrayList<RedstoneRemoteData> recvs = new ArrayList<RedstoneRemoteData>();
            recvs.add(rrd);
            this.recievers.put(name, recvs);
            BC.log.info("La til ny reciever på ny frekvens");
        }

    }

    public void removeReciever(String name, RedstoneRemoteData rrd) {
        this.recievers.get(name).remove(rrd);
    }

    public boolean hasReciever(String name) {
        if (this.recievers.containsKey(name)) {
            return true;
        } else {
            return false;
        }
    }

    public ArrayList<RedstoneRemoteData> getRecievers(String name) {
        return this.recievers.get(name);
    }

    public HashMap<String, ArrayList<RedstoneRemoteData>> getRecieverHash() {
        return this.recievers;
    }
}