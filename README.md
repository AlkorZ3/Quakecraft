### This version of QuakeCraft is a fork from bl4ckskull666
### Thanks to Geekpower14 for the original code and thanks to bl4ckskull666 for the resurection!
### This source code is freely available for everybody. The only condition is to respect the GPL license and keep track of the previous authors into the plugin.yml.

![Quake Plugin](https://media.forgecdn.net/avatars/67/696/636163107094889338.png)

# Quake_v3.5.0-Alpha
This Quake Version is for Minecraft Spigot 1.16.5.

####################################################

## Commands
* /quake help - All commands

## Permissions
* Quake.player - For a default player.
* Quake.JoinInGame - Join in game.
* Quake.modo - Start/stop for moderators.
* Quake.edit - To manage arenas.
* Quake.lobby - To manage lobby.
* Quake.VIP - To double gain ! and other stuff (join full games ...)
* Quake.Shop - To allow automatic give of emerald in selected world.
* Quake.admin - Allow all permissions.

## Features
* Multiple arenas
* Multiple Lobby area and dynamic Lobby wall!
* Economy support / Self economy
* Shop
* Timer
* ScoreBoard
* Permissions
* Command completion

## Gameplay features
* Sneak is disabled, when you sneak other people see you normally (You can disable in the arena config).
* Jump Boost 2 and Speed 2 effect in the arena (You can disable in the arena config).
* Spawn-Kill reduce by 0.5 sec of invicibility at respawn.
* Others..

####################################################

## How to install ( Depended WorldEdit / Softdepended Vault ):
* Download the plugin;
* Stop the Server;
* Place it in the plugin folder (plugins/).
* Restart/Reload the server again (for create new files);
* Change config files;
* Restart the server again;
* Create new world with your arena (You need a Multi-World plugin like Multiverse) (optionally);
* Enjoy!

## How to use: ( ignore the [] , it's mark placeholder )
**Create an Arena**
* Do /quake create [Arena name] [Solo | Team] - to create an arena.
* Do /quake setmin [Arena name] [number] - to set the minimum number of player to begin the game.
* Do /quake setmax [Arena name] [number] - to set the maximum number of player can join the game.
* Do /quake setmap [Arena name] [Name of map] - to set the name of the map will display on the lobby (Unknown will be display if nothing is set).
* Do /quake addspawn [Arena name] [Read | Blue (Only for team arena)] - in the Arena, to add a randoms spawn of the Arena. (Do this many time of you want !)
* Do /quake save [Arena name] - to save the config of the Arena. Enjoy!

* Define a lobby area with WorldEdit.
* Do /quake addlobbyarea - to define the WorldEdit region as a Lobby area (more than one can be defined).
* Define a lobby wall with WorldEdit :  A Wall of Signs
* Do /quake addlobbywall - to define the WorldEdit region as a Lobby wall (more than one can be defined).
* Do /quake setlobbyspawn - to add a spawn at the end of the game (more than one can be defined).

**New Lobby System
Warning, the release 3.5.0 break the "lobby.yml" file format: you will have to redefine the lobby wall...

[Read this Page](https://github.com/Bl4ckSkull666/Quake---The-Gun-Game/wiki/Lobby-system)
