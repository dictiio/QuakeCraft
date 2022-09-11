package me.dictiooo.quakecraft.manager;

import me.dictiooo.quakecraft.QuakeCraft;
import me.dictiooo.quakecraft.commands.AddSpawnCommand;
import me.dictiooo.quakecraft.commands.JoinCommand;
import me.dictiooo.quakecraft.commands.SetLobbyCommand;
import me.dictiooo.quakecraft.commands.StartCommand;

public class CommandManager {

    public static void registerCommands(){
        QuakeCraft plugin = QuakeCraft.getInstance();
        plugin.getCommand("join").setExecutor(new JoinCommand());
        plugin.getCommand("start").setExecutor(new StartCommand());
        plugin.getCommand("setlobby").setExecutor(new SetLobbyCommand());
        plugin.getCommand("addspawn").setExecutor(new AddSpawnCommand());
    }

}
