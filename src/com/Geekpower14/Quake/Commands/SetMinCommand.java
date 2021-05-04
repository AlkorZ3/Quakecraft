/*
 * Decompiled with CFR 0_114.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.entity.Player
 */
package com.Geekpower14.Quake.Commands;

import com.Geekpower14.Quake.Arena.Arena;
import com.Geekpower14.Quake.Quake;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class SetMinCommand implements BasicCommand {
    private final Quake _plugin;

    public SetMinCommand(Quake pl) {
        _plugin = pl;
    }

    @Override
    public boolean onCommand(Player player, String[] args) {
        if (Quake.hasPermission(player, getPermission())) {
            Arena arena = null;
	    if ( args.length > 0) {
		if (_plugin._am.exist(args[0])) {
		    arena = _plugin._am.getArenabyName(args[0]);
		} else if (args[0].matches("^\\d*$")) {
		    arena = _plugin._am.getArenabyID(Integer.valueOf(args[0]));
		}
	    }
            if (arena == null) {
                player.sendMessage(ChatColor.RED + "Please type a good arena name ! !");
                return true;
            }
            if (args.length < 2) {
                player.sendMessage(ChatColor.RED + "Please type a number !");
                return true;
            }
            arena.setmin(Integer.parseInt(args[1]));
            arena.saveConfig();
            player.sendMessage(ChatColor.GREEN + "Set the minimum player with success !");
        } else {
            player.sendMessage(_plugin._trad.get("NoPermission"));
        }
        return true;
    }

    @Override
    public String getPermission() {
        return "Quake.edit";
    }

    @Override
    public String help(Player p) {
        if (Quake.hasPermission(p, getPermission())) {
            return "/quake setmin [Arena] [Number] - Set minimum player in the arena.";
        }
        return "";
    }

    @Override
    public List<String> getCompletionList(Player player, String[] args) {
	List<String> list;

	if( args.length <= 2) {
	    list = _plugin._am.getArenaNameList();
	} else {
	    list = new ArrayList();
	}

	return list;
    }
}

