package com.Geekpower14.Quake.Commands;

import com.Geekpower14.Quake.Quake;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.entity.Player;

public class AddLobbyWallCommand
implements BasicCommand {
    private final Quake _plugin;

    public AddLobbyWallCommand(Quake pl) {
        _plugin = pl;
    }

    @Override
    public boolean onCommand(Player player, String[] args) {
        if (Quake.hasPermission(player, getPermission())) {
            _plugin._lobby.addLobbyWall(player);
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
            return "/quake addlobbywall - Add a sign wall lobby.";
        }
        return "";
    }

    @Override
    public List<String> getCompletionList(Player player, String[] args) {
	List<String> list = new ArrayList();

	return list;
    }
}
