package com.Geekpower14.Quake.Arena;

import com.Geekpower14.Quake.Quake;
import com.Geekpower14.Quake.Utils.TableGenerator;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ArenaManager {
    public Quake _plugin;
    public HashMap<String, Arena> _ARENAS = new HashMap();

    public ArenaManager(Quake pl) {
        _plugin = pl;
        loadArenas();
    }

    public final void loadArenas() {
        File folder = new File(_plugin.getDataFolder(), "/arenas/");
        if (!folder.exists()) {
            folder.mkdir();
        }
        ArrayList<String> Maps = new ArrayList<>();
        for(File f: folder.listFiles()) {
	    if(f.getName().endsWith(".yml")) {
		    String name = f.getName().replaceAll(".yml", "");
		    Maps.add(name);
		}
        }
        if(Maps.isEmpty())
            return;

        for(String mapname : Maps) {
            YamlConfiguration config = YamlConfiguration.loadConfiguration(new File(_plugin.getDataFolder(), "/arenas/" + mapname + ".yml"));
            String type = "Solo";
            if(config.getString("Type", "Solo").equalsIgnoreCase("Team"))
                type = "Team";

            createArena(mapname, type);
        }
    }

    public void createArena(String name, String Type2) {
        if (name == null) {
            return;
        }
        Arena arena = null;
        if (Type2.equalsIgnoreCase("Solo")) {
            arena = new SArena(_plugin, name, _ARENAS.size());
            _ARENAS.put(name, arena);
	} else if(Type2.equalsIgnoreCase("Team")) {
            arena = new TArena(_plugin, name, _ARENAS.size());
            _ARENAS.put(name, arena);
        }
    }

    public String getPlayerhoe(String player) {
        File fichier_config = new File(_plugin.getDataFolder(), "hoe.yml");
        if(!fichier_config.exists())
            return null;
        YamlConfiguration config = YamlConfiguration.loadConfiguration((File)fichier_config);
        return config.getString(player);
    }

    public void setPlayerhoe(String player, String hoe) {
        YamlConfiguration config = YamlConfiguration.loadConfiguration((File)new File(_plugin.getDataFolder(), "hoe.yml"));
        config.set(player, hoe);
        try {
            config.save(new File(_plugin.getDataFolder(), "hoe.yml"));
            _plugin.saveConfig();
        } catch (IOException var4_4) {
            // empty catch block
        }
    }

    public void removeArena(String name) {
        Arena aren = _ARENAS.get(name);
        aren.stop();
        _ARENAS.remove(name);
    }

    public void deleteArena(String name) {
        Arena aren = _ARENAS.get(name);
        aren.stop();
        File file = new File(_plugin.getDataFolder(), "/arenas/" + aren._name + ".yml");
        file.delete();
        _ARENAS.remove(name);
    }

    public Boolean exist(String aren) {
        return _ARENAS.containsKey(aren);
    }

    public Boolean existid(int ID) {
        Arena arena = getArenabyID(ID);
        return arena != null;
    }

    public Arena getArenabyName(String name) {
        if (_ARENAS.size() < 1) {
            return null;
        }
        return _ARENAS.get(name);
    }

    public Arena getArenabyPlayer(Player player) {
        if(_ARENAS.size() < 1) {
            return null;
        }
        for(Arena aren : _ARENAS.values()) {
            if(!aren.isingame(player))
                continue;
            return aren;
        }
        return null;
    }

    public Boolean isArenaWorld(World world) {
        if (_ARENAS.size() < 1) {
            return false;
        }
        for (Arena arena : _ARENAS.values()) {
            Location wo;
            World w;
            if (arena instanceof SArena) {
                SArena aren = (SArena)arena;
                if(aren._spawns.isEmpty() || world != aren._spawns.get(0).getWorld())
                    continue;
                return true;
            }
            if (!(arena instanceof TArena))
                continue;
            TArena aren = (TArena)arena;
            if(aren._spawns_B.isEmpty() || world != aren._spawns_B.get(0).getWorld())
                continue;
            return true;
        }
        return false;
    }

    public Arena getArenabyID(int ID) {
        if (_ARENAS.size() < 1) {
            return null;
        }
        for(Arena aren : _ARENAS.values()) {
            if (aren._ID != ID)
                continue;
            return aren;
        }
        return null;
    }

    public void reloadArenas() {
        for(Arena aren : _ARENAS.values()) {
            aren.reloadConfig();
        }
    }

    public void Disable() {
        for(Arena aren : _ARENAS.values()) {
            aren.disable();
        }
    }

    public void updateScoreArenas() {
        for(Arena aren : _ARENAS.values()) {
            aren.updateScore();
        }
    }

    public List getArenaNameList() {
	List<String> list = new ArrayList();
	
	if(_ARENAS.size() > 0) {
	    list = new ArrayList<>(_ARENAS.keySet());
	}
	return list;
    }
    
    public void listArenas(Player player) {
	String type, status;
	
	if(_ARENAS.size() < 1) {
	    player.sendMessage("No Arena!");
	}
	else {
	    player.sendMessage(ChatColor.LIGHT_PURPLE +"Arena List:");
	    player.sendMessage("");

	    TreeMap<Integer, Arena> _ARENAS_sorted = new TreeMap();
	    for(Arena aren : _ARENAS.values()) {
		_ARENAS_sorted.put( aren._ID, aren);
	    }

	    TableGenerator tg = new TableGenerator(TableGenerator.Alignment.RIGHT, TableGenerator.Alignment.LEFT, TableGenerator.Alignment.LEFT, TableGenerator.Alignment.CENTER, TableGenerator.Alignment.RIGHT, TableGenerator.Alignment.CENTER, TableGenerator.Alignment.RIGHT);
	    tg.addRow( ChatColor.RED + "§nID§r" + ChatColor.WHITE, ChatColor.GREEN + "§nType§r", "§nArena Name§r" + ChatColor.WHITE, ChatColor.GREEN + "§nStatus§r" + ChatColor.WHITE, ChatColor.GOLD + "§nNb P§r", ChatColor.BLUE + "§nMin" + ChatColor.WHITE + "§n/" +  ChatColor.RED + "§nMax§r", "§nSpawn§r");
	    tg.addRow();
	    
	    for(Arena aren : _ARENAS_sorted.values()) {
		if(aren instanceof SArena) {
		    type="Solo";
		} else {
		    type="Team";
		}
		
		tg.addRow( ChatColor.RED + Integer.toString(aren._ID) + ChatColor.WHITE, ChatColor.GREEN + type , aren._name + ChatColor.WHITE, aren.getStatus(false),
			   ChatColor.GOLD + Integer.toString( aren._players.size()), ChatColor.BLUE + Integer.toString( aren._minplayer) + ChatColor.WHITE + " / " + ChatColor.RED + Integer.toString( aren._maxplayer) + ChatColor.WHITE, type == "Solo" ? Integer.toString( aren._spawns.size()) : Integer.toString( aren._spawns_B.size()) + " + " + Integer.toString( aren._spawns_R.size()));
		
	    }
	    
	    for (String line : tg.generate(TableGenerator.Receiver.CLIENT, true, true)) {
		player.sendMessage(line);
	    }		
	}
    }
}
