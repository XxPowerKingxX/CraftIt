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

package me.thypthon.listeners;

import me.thypthon.BC;
import me.thypthon.handlers.config.ConfigurationHandler;
import me.thypthon.handlers.users.UserHandler;
import org.bukkit.ChatColor;
import org.schwering.irc.lib.IRCConnection;
import org.schwering.irc.lib.IRCEventListener;
import org.schwering.irc.lib.IRCModeParser;
import org.schwering.irc.lib.IRCUser;
import org.schwering.irc.lib.ssl.SSLIRCConnection;
import org.schwering.irc.lib.ssl.SSLTrustManager;

import java.io.IOException;
import java.security.cert.X509Certificate;
import java.util.HashSet;


public class BCIRCListener extends Thread {

    private BC plugin;
    private UserHandler userHandler;
    private HashSet<String> fuusers = new HashSet<String>();
    private IRCConnection conn;

    public BCIRCListener(BC instance) {
        // Fubar ircbots.
        fuusers.add("CraftIt");

        this.plugin = instance;
        this.userHandler = instance.getUserHandler();
    }

    public void initialize() {
        ConfigurationHandler cfg = this.plugin.getGlobalStateManager();
        if (cfg.irc) {
            if (!cfg.botssl) {
                conn = new IRCConnection(cfg.botserver, cfg.botport, cfg.botport, "", cfg.botnick, cfg.botnick, "BC.no Bot");
            } else {
                conn = new SSLIRCConnection(cfg.botserver, cfg.botport, cfg.botport, "", cfg.botnick, cfg.botnick, "BC.no Bot");
                ((SSLIRCConnection) conn).addTrustManager(new TrustManager());
            }
            conn.addIRCEventListener(new Listener());
            conn.setEncoding("UTF-8");
            conn.setPong(true);
            conn.setDaemon(false);
            conn.setColors(false);
            try {
                conn.connect();
            } catch (IOException e) {
                e.printStackTrace();
            }
            start();
        }
    }

    public void exit() {
        conn.close();
    }

    public IRCConnection getIRCConnection() {
        return conn;
    }

    public class TrustManager implements SSLTrustManager {
        private X509Certificate[] chain;

        public X509Certificate[] getAcceptedIssuers() {
            return chain != null ? chain : new X509Certificate[0];
        }

        public boolean isTrusted(X509Certificate[] chain) {
            return true;
        }
    }

    public class Listener implements IRCEventListener {

        public void onRegistered() {
            // Koblet til serveren
            ConfigurationHandler cfg = plugin.getGlobalStateManager();
            // Nickserv
            conn.doPrivmsg("nickserv", "identify " + cfg.botnickservpass);
            conn.doJoin(cfg.botchannel);
        }

        public void onDisconnected() {
            // Mistet kobling til serveren
            conn.close();
            try {
                conn.connect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void onError(String msg) {
            BC.log.severe("[BC] IRC Error: " + msg);
        }

        public void onError(int num, String msg) {
            BC.log.severe("[BC] IRC Error: " + num + ", " + msg);
        }

        public void onJoin(String chan, IRCUser u) {
            ConfigurationHandler cfg = plugin.getGlobalStateManager();
            if(chan.equalsIgnoreCase(cfg.botchannel) && !fuusers.contains(u.getNick())) {
                plugin.broadcastAll(ChatColor.RED + u.getNick() + ChatColor.GREEN + " logged in (irc).");
            }
        }

        public void onPart(String chan, IRCUser u, String msg) {
            ConfigurationHandler cfg = plugin.getGlobalStateManager();
            if(chan.equalsIgnoreCase(cfg.botchannel) && !fuusers.contains(u.getNick())) {
                plugin.broadcastAll(ChatColor.RED + u.getNick() + ChatColor.RED + " logged out (irc).");
            }
        }

        public void onPrivmsg(String chan, IRCUser u, String msg) {
            ConfigurationHandler cfg = plugin.getGlobalStateManager();
            if (cfg.botchannel.equalsIgnoreCase(chan) && !fuusers.contains(u.getNick())) {
                plugin.broadcastAll(ChatColor.RED + u.getNick() + ": " + msg);
            }
        }

        public void onInvite(String chan, IRCUser u, String inviter) {
            // Bot blir invitert
        }

        public void onKick(String chan, IRCUser u, String kicked, String msg) {
            // En bruker blir kicket.
        }


        public void onMode(IRCUser u, String nickPass, String mode) {
            // Mode skiftes
        }

        public void onMode(String chan, IRCUser u, IRCModeParser mp) {
            // Mode skiftes
        }

        public void onNick(IRCUser u, String nickNew) {
            // Bruker skifter nick.
        }

        public void onNotice(String target, IRCUser u, String msg) {
            // En bruker sender botten en notice
        }

        public void onQuit(IRCUser u, String msg) {
            @SuppressWarnings("unused")
			ConfigurationHandler cfg = plugin.getGlobalStateManager();
            if(!fuusers.contains(u.getNick())) {
                plugin.broadcastAll(ChatColor.RED + u.getNick() + " logged out (irc).");
            }
        }

        public void onReply(int num, String value, String msg) {
            // MSG
        }

        public void onTopic(String chan, IRCUser u, String topic) {
            // Topic skiftes
        }

        public void onPing(String p) {
            // Noen pinger oss :o
        }

        public void unknown(String a, String b, String c, String d) {
            // Ukjent (RAW DATA)
        }

    }
}