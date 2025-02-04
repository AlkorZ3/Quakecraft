package com.Geekpower14.Quake.Utils;

import com.Geekpower14.Quake.Quake;
import com.Geekpower14.Quake.Trans.Score;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.ChatColor;

public final class ScoreB {
    public Quake _plugin;
    public int _Money = 0;
    public int _Kills = 0;
    public int _Deaths = 0;
    public int _Shots = 0;
    public int _Wins = 0;
    public Player _player;
    public Objective _objective;
    public Scoreboard _board;

    public ScoreB(Quake pl, Player p) {
        _plugin = pl;
        _player = p;
        _Kills = _plugin._eco.getScore(p, Score.Type.Kill);
        _Deaths = _plugin._eco.getScore(p, Score.Type.Death);
        _Shots = _plugin._eco.getScore(p, Score.Type.Shot);
        _Wins = _plugin._eco.getScore(p, Score.Type.Win);
        _Money = _plugin._eco.getPlayerMoney(p);
        _board = Bukkit.getScoreboardManager().getNewScoreboard();
        _objective = _board.registerNewObjective("Score", "dummy");
        _objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        _objective.setDisplayName(ChatColor.YELLOW + _plugin._trad.get("ScoreBoard.name"));
        _objective.getScore(_plugin._trad.get("Shop.Coins.name")).setScore(_Money);
        _objective.getScore(_plugin._trad.get("ScoreBoard.Kills.name")).setScore(_Kills);
        _objective.getScore(_plugin._trad.get("ScoreBoard.Deaths.name")).setScore(_Deaths);
        _objective.getScore(_plugin._trad.get("ScoreBoard.Shots.name")).setScore(_Shots);
        _objective.getScore(_plugin._trad.get("ScoreBoard.Wins.name")).setScore(_Wins);

	displayScoreB( "");
    }

    public void updateScore() {
        if( _Shots != _plugin._eco.getScore(_player, Score.Type.Shot) || _Deaths != _plugin._eco.getScore(_player, Score.Type.Death) || _Money != _plugin._eco.getPlayerMoney(_player)) {
	    _Kills = _plugin._eco.getScore(_player, Score.Type.Kill);
	    _Deaths = _plugin._eco.getScore(_player, Score.Type.Death);
	    _Shots = _plugin._eco.getScore(_player, Score.Type.Shot);
	    _Wins = _plugin._eco.getScore(_player, Score.Type.Win);
	    _Money = _plugin._eco.getPlayerMoney(_player);
	    _objective.getScore(_plugin._trad.get("Shop.Coins.name")).setScore(_Money);
	    _objective.getScore(_plugin._trad.get("ScoreBoard.Kills.name")).setScore(_Kills);
	    _objective.getScore(_plugin._trad.get("ScoreBoard.Deaths.name")).setScore(_Deaths);
	    _objective.getScore(_plugin._trad.get("ScoreBoard.Shots.name")).setScore(_Shots);
	    _objective.getScore(_plugin._trad.get("ScoreBoard.Wins.name")).setScore(_Wins);
	}

	displayScoreB( "");
    }

    public Scoreboard getScoreBoard() {
        return _board;
    }

    public void displayScoreB(String target_world) {
	String world;
	
	if( target_world != "") {
	    world = target_world;
	} else {
	    world = _player.getWorld().getName();
	}
	
	if( isQuakeWorld( world)) {
	    if( _plugin._lobby.isinLobbyArea(_player.getLocation())) {
		_player.setScoreboard(_board);
	    } else {
		_player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
	    }
	} else {
	    _player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
	}
	
	return;
    }
    
    public Boolean isQuakeWorld(String name) {
        return _plugin._QuakeWorlds.contains(name);
    }
}

