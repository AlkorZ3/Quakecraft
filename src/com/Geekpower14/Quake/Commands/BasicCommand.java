/*
 * Decompiled with CFR 0_114.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 */
package com.Geekpower14.Quake.Commands;

import java.util.List;
import org.bukkit.entity.Player;

public interface BasicCommand {
    public boolean onCommand(Player var1, String[] var2);

    public String getPermission();

    public String help(Player var1);

    public List<String> getCompletionList(Player player, String[] args);
}

