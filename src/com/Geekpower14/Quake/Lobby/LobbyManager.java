package com.Geekpower14.Quake.Lobby;

import com.Geekpower14.Quake.Arena.Arena;
import com.Geekpower14.Quake.Arena.SArena;
import com.Geekpower14.Quake.Arena.TArena;
import com.Geekpower14.Quake.Quake;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.regions.Region;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class LobbyManager {
    public Quake _plugin;
    public HashMap<String, Location> _arealocmin = new HashMap();
    public HashMap<String, Location> _arealocmax = new HashMap();
    public HashMap<String, Location> _walllocmin = new HashMap();
    public HashMap<String, Location> _walllocmax = new HashMap();
    public Location _lobbyspawn = null;
    public List<Lobby> _LOBBYS = new ArrayList<>();
    public List<Lobby_Sign> _LOBBYS_SIGN = new ArrayList<>();

    public LobbyManager(Quake pl) {
        _plugin = pl;
        loadconfig();
    }

    public final void loadconfig() {
        String name;
	int number,i;
        File config_file = new File(_plugin.getDataFolder(), String.valueOf(File.separator) + "lobby.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration((File)config_file);
        _lobbyspawn = str2loc(config.getString("LobbySpawn"));

        number = config.getInt("LobbyAreaNb");
        i = 0;
        while (i < number) {
            name = config.getString("min.lobbyarea" + i);
            _arealocmin.put("lobbyarea" + i, str2loc(name));
            ++i;
        }
        i = 0;
        while (i < number) {
            name = config.getString("max.lobbyarea" + i);
            _arealocmax.put("lobbyarea" + i, str2loc(name));
            ++i;
        }
        _plugin.getLogger().info("Lobby Area Loaded: " + number + "!");

        number = config.getInt("LobbyWallNb");
        i = 0;
        while (i < number) {
            name = config.getString("min.lobbywall" + i);
            _walllocmin.put("lobbywall" + i, str2loc(name));
            ++i;
        }
        i = 0;
        while (i < number) {
            name = config.getString("max.lobbywall" + i);
            _walllocmax.put("lobbywall" + i, str2loc(name));
            ++i;
        }
        _plugin.getLogger().info("Lobby Wall Loaded: " + number + "!");
    }

    public void saveconfig() {
        Location loc;
        File config_file = new File(_plugin.getDataFolder(), String.valueOf(File.separator) + "lobby.yml");

	config_file.delete();

	if(!config_file.exists()) {
	    try {
		config_file.createNewFile();
	    } catch(IOException e) {
		// empty catch block
	    }
	}
        YamlConfiguration config = YamlConfiguration.loadConfiguration(config_file);

	
        if(_lobbyspawn != null) {
            config.set("LobbySpawn", (String.valueOf(_lobbyspawn.getWorld().getName()) + ", " + _lobbyspawn.getX() + ", " + _lobbyspawn.getY() + ", " + _lobbyspawn.getZ() + ", " + _lobbyspawn.getYaw() + ", " + _lobbyspawn.getPitch()));
        }
        
        config.set("LobbyAreaNb", _arealocmin.size());
        config.set("LobbyWallNb", _walllocmin.size());

        for(String l2 : _arealocmin.keySet()) {
            loc = _arealocmin.get(l2);
            config.set("min." + l2, (String.valueOf(loc.getWorld().getName()) + ", " + loc.getX() + ", " + loc.getY() + ", " + loc.getZ() + ", " + loc.getYaw() + ", " + loc.getPitch()));
        }
        
        for(String l2 : _arealocmax.keySet()) {
            loc = _arealocmax.get(l2);
            config.set("max." + l2, (String.valueOf(loc.getWorld().getName()) + ", " + loc.getX() + ", " + loc.getY() + ", " + loc.getZ() + ", " + loc.getYaw() + ", " + loc.getPitch()));
        }
        
        for(String l2 : _walllocmin.keySet()) {
            loc = _walllocmin.get(l2);
            config.set("min." + l2, (String.valueOf(loc.getWorld().getName()) + ", " + loc.getX() + ", " + loc.getY() + ", " + loc.getZ() + ", " + loc.getYaw() + ", " + loc.getPitch()));
        }
        
        for(String l2 : _walllocmax.keySet()) {
            loc = _walllocmax.get(l2);
            config.set("max." + l2, (String.valueOf(loc.getWorld().getName()) + ", " + loc.getX() + ", " + loc.getY() + ", " + loc.getZ() + ", " + loc.getYaw() + ", " + loc.getPitch()));
        }
        
        try {
            config.save(new File(_plugin.getDataFolder(), String.valueOf(File.separator) + "lobby.yml"));
            _plugin.saveConfig();
        } catch (IOException l) {
            // empty catch block
        }
        _plugin.getLogger().info("Lobby config saved!");
    }

    public Location str2loc(String loc) {
        if (loc == null) {
            return null;
        }
        Location res = null;
        String[] loca = loc.split(", ");
        res = new Location(_plugin.getServer().getWorld(loca[0]), Double.parseDouble(loca[1]), Double.parseDouble(loca[2]), Double.parseDouble(loca[3]), Float.parseFloat(loca[4]), Float.parseFloat(loca[5]));
        return res;
    }

    public void setspawn(Player player) {
        _lobbyspawn = player.getLocation();
    }

    public Boolean addLobbyArea(Player player) {
        String lobby = "lobbyarea" + _arealocmin.size();
        try {
            LocalSession ls = WorldEdit.getInstance().getSessionManager().get(BukkitAdapter.adapt(player));
            Region reg = ls.getSelection(BukkitAdapter.adapt(player.getWorld()));
            
            if(reg != null) {
                _arealocmin.put(lobby, BukkitAdapter.adapt(player.getWorld(), reg.getMinimumPoint()));
                _arealocmax.put(lobby, BukkitAdapter.adapt(player.getWorld(), reg.getMaximumPoint()));
                player.sendMessage(ChatColor.YELLOW + "Lobby " + (_arealocmin.size() - 1) + " successfully created.");
                return true;
            }
        } catch(Exception ex) {
            player.sendMessage(ChatColor.RED + "Internal error on region selection.");
        }
        return false;
    }

    public Boolean removeLobbyArea(String lobby) {
        _arealocmin.remove(lobby);
        _arealocmax.remove(lobby);
        HashMap<String, Location> tempmin = new HashMap<>();
        HashMap<String, Location> tempmax = new HashMap<>();
        int i = 0;
        for (String s : _arealocmin.keySet()) {
            tempmin.put("lobbyarea" + i, _arealocmin.get(s));
            tempmax.put("lobbyarea" + i, _arealocmax.get(s));
            ++i;
        }
        _arealocmin = tempmin;
        _arealocmax = tempmax;
        return true;
    }

    public List getLobbyAreaIDList() {
	List<String> list = new ArrayList();
	
	for (int i = 0; i < _arealocmin.size(); i++) {
	    list.add(String.valueOf(i));
	}

	return list;
    }
    
    public Boolean addLobbyWall(Player player) {
        String lobby = "lobbywall" + _walllocmin.size();
        try {
            LocalSession ls = WorldEdit.getInstance().getSessionManager().get(BukkitAdapter.adapt(player));
            Region reg = ls.getSelection(BukkitAdapter.adapt(player.getWorld()));
            
            if(reg != null) {
                _walllocmin.put(lobby, BukkitAdapter.adapt(player.getWorld(), reg.getMinimumPoint()));
                _walllocmax.put(lobby, BukkitAdapter.adapt(player.getWorld(), reg.getMaximumPoint()));
                player.sendMessage(ChatColor.YELLOW + "Lobby wall " + (_walllocmin.size() - 1) + " successfully created.");
                initsign();
                return true;
            }
        } catch(Exception ex) {
            player.sendMessage(ChatColor.RED + "Internal error on region selection.");
        }
        return false;
    }

    public Boolean removeLobbyWall(String lobbywall) {
        _walllocmin.remove(lobbywall);
        _walllocmax.remove(lobbywall);
        HashMap<String, Location> tempmin = new HashMap<>();
        HashMap<String, Location> tempmax = new HashMap<>();
        int i = 0;
        for (String s : _walllocmin.keySet()) {
            tempmin.put("lobbywall" + i, _walllocmin.get(s));
            tempmax.put("lobbywall" + i, _walllocmax.get(s));
            ++i;
        }
        _walllocmin = tempmin;
        _walllocmax = tempmax;
        return true;
    }

    public List getLobbyWallIDList() {
	List<String> list = new ArrayList();
	
	for (int i = 0; i < _walllocmin.size(); i++) {
	    list.add(String.valueOf(i));
	}

	return list;
    }
    
    public Boolean initsign() {
        int index = 0;
        int i = 0;
        while(i < _walllocmin.size()) {
            int x;
            int y;
            Arena arena;
            String lobby = "lobbywall" + i;
            Location min = _walllocmin.get(lobby);
            Location max = _walllocmax.get(lobby);
            int xmax = 0;
            int xmin = 0;
            int z = 0;
            World w = min.getWorld();
            if (w == null) {
                return false;
            }
            if (min.getBlockX() == max.getBlockX()) {
                xmax = max.getBlockZ();
                xmin = min.getBlockZ();
                z = min.getBlockX();
                x = xmin;
                while (x <= xmax) {
                    y = min.getBlockY();
                    while (y <= max.getBlockY()) {
                        arena = _plugin._am.getArenabyID(index);
                        if (arena != null) {
                            updateSign(w, z, y, x, arena);
                        } else {
                            clearSign(w, z, y, x);
                        }
                        ++index;
                        ++y;
                    }
                    ++x;
                }
            } else if (min.getBlockZ() == max.getBlockZ()) {
                xmax = max.getBlockX();
                xmin = min.getBlockX();
                z = min.getBlockZ();
                x = xmin;
                while (x <= xmax) {
                    y = max.getBlockY();
                    while (y >= min.getBlockY()) {
                        arena = _plugin._am.getArenabyID(index);
                        if (arena != null) {
                            updateSign(w, x, y, z, arena);
                        } else {
                            clearSign(w, x, y, z);
                        }
                        ++index;
                        --y;
                    }
                    ++x;
                }
            }
            ++i;
        }
        return true;
    }

    public Boolean isinLobbyArea(Location loc) {
        int yes = _arealocmin.size();
        int i = 0;
        while (i < _arealocmin.size()) {
            String lobby = "lobbyarea" + i;
            Location min = _arealocmin.get(lobby);
            Location max = _arealocmax.get(lobby);
            if (min.getWorld() != loc.getWorld()) {
                --yes;
            } else if (loc.getX() < min.getX()) {
                --yes;
            } else if (loc.getX() > max.getX()) {
                --yes;
            } else if (loc.getZ() < min.getZ()) {
                --yes;
            } else if (loc.getZ() > max.getZ()) {
                --yes;
            } else if (loc.getY() < min.getY()) {
                --yes;
            } else if (loc.getY() > max.getY()) {
                --yes;
            }
            ++i;
        }
        if (yes <= 0) {
            return false;
        }
        return true;
    }

    public Boolean isinLobbyWall(Location loc) {
        int yes = _walllocmin.size();
        int i = 0;
        while (i < _walllocmin.size()) {
            String lobby = "lobbywall" + i;
            Location min = _walllocmin.get(lobby);
            Location max = _walllocmax.get(lobby);
            if (min.getWorld() != loc.getWorld()) {
                --yes;
            } else if (loc.getX() < min.getX()) {
                --yes;
            } else if (loc.getX() > max.getX()) {
                --yes;
            } else if (loc.getZ() < min.getZ()) {
                --yes;
            } else if (loc.getZ() > max.getZ()) {
                --yes;
            } else if (loc.getY() < min.getY()) {
                --yes;
            } else if (loc.getY() > max.getY()) {
                --yes;
            }
            ++i;
        }
        if (yes <= 0) {
            return false;
        }
        return true;
    }

    public void updateSign(World w, int x, int y, int z, Arena arena) {
        Location bloc = new Location(w, (double)x, (double)y, (double)z);
        if(bloc.getBlock() == null || bloc.getBlock().getType().equals(Material.AIR))
            return;

        Block block = bloc.getBlock();
        if (block.getState() instanceof Sign) {
            Sign sign = (Sign)block.getState();
	    /*
	      String ligne0 = arena._etat <= arena._pregame ? (arena.getplayers() == arena._maxplayer ? ChatColor.DARK_PURPLE + "[FULL]" : (arena._VIP ? ChatColor.AQUA + "[VIP]" : (arena._etat <= arena._starting ? ChatColor.GOLD + "[Starting]" : ChatColor.GREEN + "[Join]"))) : ChatColor.RED + "[InGame]";
	    */
            String ligne0 = arena.getStatus(true);
            String tmp = "";
            if (arena instanceof SArena) {
                tmp = "S-";
            }
            if (arena instanceof TArena) {
                tmp = "T-";
            }
            String ligne1 = tmp + arena._ID;
            String ligne2 = (arena.getplayers() >= (arena._maxplayer * 0.75))? "" + ChatColor.RED + arena.getplayers() + "/" + arena._maxplayer : "" + arena.getplayers() + "/" + arena._maxplayer;
            String ligne3 = arena._map;
            sign.setLine(0, ligne0);
            sign.setLine(1, ligne1);
            sign.setLine(2, ligne2);
            sign.setLine(3, ligne3);
            sign.update(true);
        }
    }

    public void clearSign(World w, int x, int y, int z) {
        Location bloc = new Location(w, (double)x, (double)y, (double)z);
        Block block = bloc.getBlock();
        if (block.getState() instanceof Sign) {
            Sign sign = (Sign)block.getState();
            sign.setLine(0, ChatColor.GRAY + "[" + ChatColor.RED + "Quake" + ChatColor.GRAY + "]");
            sign.setLine(1, "");
            sign.setLine(2, ChatColor.GRAY + "Offline");
            sign.setLine(3, "");
            sign.update(true);
        }
    }
}

