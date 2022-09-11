package me.dictiooo.quakecraft;

import me.dictiooo.quakecraft.data.SpawnConfigManager;
import me.dictiooo.quakecraft.game.Game;
import me.dictiooo.quakecraft.manager.CommandManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class QuakeCraft extends JavaPlugin {

    private static QuakeCraft instance;
    private Game game;
    @Override
    public void onEnable() {
        instance = this;
        game = new Game();
        CommandManager.registerCommands();
        SpawnConfigManager.saveDefaultConfig();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static QuakeCraft getInstance(){
        return instance;
    }

    public Game getGame(){
        return game;
    }
}
