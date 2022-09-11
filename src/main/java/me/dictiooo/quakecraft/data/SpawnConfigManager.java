package me.dictiooo.quakecraft.data;

import me.dictiooo.quakecraft.QuakeCraft;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;

public class SpawnConfigManager {

    private static QuakeCraft plugin = QuakeCraft.getInstance();
    private static FileConfiguration spawnPointConfig = null;
    private static File spawnPointConfigFile = null;

    public static void reloadConfig(){
        if(spawnPointConfigFile == null)
            spawnPointConfigFile = new File(plugin.getDataFolder(), "spawnpoints.yml");
        spawnPointConfig = YamlConfiguration.loadConfiguration(spawnPointConfigFile);
        InputStream defaultStream = plugin.getResource("spawnpoints.yml");
        if(defaultStream != null){
            YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultStream));
            spawnPointConfig.setDefaults(defaultConfig);
        }
    }

    public static FileConfiguration getConfig(){
        if (spawnPointConfig == null)
            reloadConfig();
        return spawnPointConfig;
    }

    public static void saveConfig(){
        if(spawnPointConfig == null || spawnPointConfigFile == null)
            return;

        try{
            getConfig().save(spawnPointConfigFile);
        } catch (IOException e){
            plugin.getLogger().log(Level.SEVERE, "Could not save config to " + spawnPointConfigFile, e);
        }

    }

    public static void saveDefaultConfig(){
        if (spawnPointConfigFile == null)
            spawnPointConfigFile = new File(plugin.getDataFolder(), "spawnpoints.yml");

        if(!spawnPointConfigFile.exists()){
            plugin.saveResource("spawnpoints.yml", false);
        }
    }



}
