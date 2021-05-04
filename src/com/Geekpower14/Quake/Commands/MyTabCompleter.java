package com.Geekpower14.Quake.Commands;

import com.Geekpower14.Quake.Quake;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

public class MyTabCompleter implements TabCompleter
{
    public Quake _plugin;

    public MyTabCompleter(Quake pl) {
        _plugin = pl;
    }

    @Override
    public List<String> onTabComplete (CommandSender sender, Command cmd, String label, String[] args) {
	List<String> list = new ArrayList<>();
	//       	_plugin.getLogger().info("MTC [" + cmd + "] [" + label + "] [" + args.length + "] [[" + Arrays.toString(args) + "]] ["+ args[args.length - 1] + "]");
   	
	if(cmd.getName().equalsIgnoreCase("quake") && args.length >= 0){
	    if(sender instanceof Player){
		list = _plugin._ce.getCompletionList((Player)sender, args);

		if( (list != null ) && (args[args.length - 1] != "")) {
		    Stream<String> s = list.stream().filter(name -> name.startsWith(args[args.length - 1]));
		    list = s.collect(Collectors.toList());
		}
	    }
	}
	return list;
    }
}
