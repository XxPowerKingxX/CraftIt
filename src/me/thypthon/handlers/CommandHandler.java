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

package me.thypthon.handlers;

import me.thypthon.BC;
import me.thypthon.handlers.users.UserHandler;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class CommandHandler implements CommandExecutor {

    protected final BC plugin;
    private ChatColor errorChatColor;
    private ChatColor okChatColor;
    private ChatColor varChatColor;
    private ChatColor defaultChatColor;
    private ChatColor commandChatColor;
    private ChatColor argChatColor;
    private int status;
    protected UserHandler userHandler;

    public CommandHandler(BC instance) {
        this.plugin = instance;
        this.userHandler = instance.getUserHandler();
        this.setErrorChatColor(ChatColor.RED);
        this.setOkChatColor(ChatColor.GREEN);
        this.setVarChatColor(ChatColor.WHITE);
        this.setDefaultChatColor(ChatColor.DARK_GREEN);
        this.setCommandChatColor(ChatColor.GOLD);
        this.setArgChatColor(ChatColor.GRAY);
        this.setStatus(15);
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (CommandHandler.isPlayer(sender)) {
            Player p = (Player) sender;
            if (this.userHandler.getUserStatus(p) >= getStatus() || p.isOp()) {
                return onPlayerCommand(p, command, label, args);
            }
        }
        return true;
    }

    public abstract boolean onPlayerCommand(Player p, Command command,
                                            String label, String[] args);

    public static boolean isPlayer(CommandSender sender) {
        if (sender instanceof Player)
            return true;
        return false;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return this.status;
    }

    public void setErrorChatColor(ChatColor errorChatColor) {
        this.errorChatColor = errorChatColor;
    }

    public ChatColor getErrorChatColor() {
        return this.errorChatColor;
    }

    public void setOkChatColor(ChatColor okChatColor) {
        this.okChatColor = okChatColor;
    }

    public ChatColor getOkChatColor() {
        return this.okChatColor;
    }

    public BC getInstance() {
        return this.plugin;
    }

    public void setVarChatColor(ChatColor varChatColor) {
        this.varChatColor = varChatColor;
    }

    public ChatColor getVarChatColor() {
        return this.varChatColor;
    }

    public void setDefaultChatColor(ChatColor defaultChatColor) {
        this.defaultChatColor = defaultChatColor;
    }

    public ChatColor getDefaultChatColor() {
        return this.defaultChatColor;
    }

    public void setCommandChatColor(ChatColor commandChatColor) {
        this.commandChatColor = commandChatColor;
    }

    public ChatColor getCommandChatColor() {
        return this.commandChatColor;
    }

    public void setArgChatColor(ChatColor argChatColor) {
        this.argChatColor = argChatColor;
    }

    public ChatColor getArgChatColor() {
        return this.argChatColor;
    }

	public boolean onPlayerCommand(Player p, Command command, String label,
			Material args) {
		// TODO Auto-generated method stub
		return false;
	}

}