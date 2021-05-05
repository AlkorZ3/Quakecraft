/*
 * Decompiled with CFR 0_114.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.entity.Player
 */
package com.Geekpower14.Quake.Commands;

import com.Geekpower14.Quake.Quake;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class RemoveLobbyWallCommand implements BasicCommand {
    private final Quake _plugin;

    public RemoveLobbyWallCommand(Quake pl) {
        _plugin = pl;
    }

    @Override
    public boolean onCommand(Player player, String[] args) {
        if (Quake.hasPermission(player, getPermission())) {
            if (args.length != 1) {
                player.sendMessage(ChatColor.RED + "Please type a good lobby wall ID!");
                return true;
            }
            int lobbywall = Integer.valueOf(args[0]);
            _plugin._lobby.removeLobbyWall("lobbywall " + lobbywall);
            player.sendMessage(ChatColor.YELLOW + "Lobby wall number " + lobbywall + " successfully removed.");
            _plugin._lobby.saveconfig();
        } else {
            player.sendMessage(_plugin._trad.get("NoPermission"));
        }
        return true;
    }

    @Override
    public String getPermission() {
        return "Quake.lobby";
    }

    @Override
    public String help(Player p) {
        if (Quake.hasPermission(p, getPermission())) {
            return "/quake removelobbywall [ID of the lobby wall] - Remove a lobby wall.";
        }
        return "";
    }

    @Override
    public List<String> getCompletionList(Player player, String[] args) {
	List<String> list;

	if( args.length <= 2) {

	    list = _plugin._lobby.getLobbyWallIDList();
	} else {
	    list = new ArrayList();
	}

	return list;
    }
}

