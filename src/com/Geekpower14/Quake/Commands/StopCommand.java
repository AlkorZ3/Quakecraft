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

public class StopCommand implements BasicCommand {
    private final Quake _plugin;

    public StopCommand(Quake pl) {
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
		    arena = _plugin._am.getArenabyID(Integer.parseInt(args[0]));
		}
	    }
            
            if (arena == null) {
                player.sendMessage(ChatColor.RED + "Please type a good arena name ! !");
                return true;
            }
            
            arena.stop();
            player.sendMessage(ChatColor.RED + "Force stop for the arena : " + args[0]);
        } else {
            player.sendMessage(_plugin._trad.get("NoPermission"));
        }
        return true;
    }

    @Override
    public String getPermission() {
        return "Quake.modo";
    }

    @Override
    public String help(Player p) {
        if (Quake.hasPermission(p, getPermission())) {
            return "/quake stop [Arena] - Force stop an arena.";
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

