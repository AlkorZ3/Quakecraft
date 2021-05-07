package com.Geekpower14.Quake;

import com.Geekpower14.Quake.Arena.Arena;
import com.Geekpower14.Quake.Arena.SArena;
import com.Geekpower14.Quake.Arena.TArena;
import com.Geekpower14.Quake.Utils.ScoreB;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Task implements Runnable {
    private final Quake _plugin;

    public Task(Quake pl) {
        _plugin = pl;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void resetTime(Arena arena) {
        Location wo = null;
        if(arena instanceof TArena) {
            if(((TArena)arena)._spawns_B.isEmpty())
                return;
            wo = ((TArena)arena)._spawns_B.get(0);
        } else if(arena instanceof SArena) {
            if(((SArena)arena)._spawns.isEmpty())
                return;
            wo = ((SArena)arena)._spawns.get(0);
        }
        if(wo == null || wo.getWorld() == null || wo.getWorld().getTime() < 10000)
            return;
        wo.getWorld().setTime(4000);
        _plugin.getLogger().info("Time set for :" + arena._name);
    }

    @Override
    public void run() {
        for(Player p: Bukkit.getOnlinePlayers()) {
        //while (n2 < n) {
            Arena arena = _plugin._am.getArenabyPlayer(p);
            if (arena != null) {
                resetTime(arena);
                return;
            }
 
            if (isQuakeWorld(p.getWorld().getName())) {
		_plugin._shop.displayShopItem( p, "");
		giveScoreBoard(p);
            }
        }
    }

    public void giveScoreBoard(Player p) {
        if (_plugin._scores.containsKey(p.getName())) {
            _plugin._scores.get(p.getName()).updateScore();
        } else {
            _plugin._scores.put(p.getName(), new ScoreB(_plugin, p));
        }
    }

    public Boolean isQuakeWorld(String name) {
        if (_plugin._QuakeWorlds.contains(name)) {
            return true;
        }
        return false;
    }
}

