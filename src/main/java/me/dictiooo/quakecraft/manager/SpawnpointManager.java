package me.dictiooo.quakecraft.manager;

import me.dictiooo.quakecraft.data.SpawnConfigManager;
import me.dictiooo.quakecraft.player.QuakePlayer;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SpawnpointManager {

    public static void setLobby(Location loc){
        SpawnConfigManager.getConfig().set("location.lobby", loc);
        SpawnConfigManager.saveConfig();
    }

    public static void addSpawn(Location loc){
        ConfigurationSection section = SpawnConfigManager.getConfig().getConfigurationSection("location.mapspawns");

        int index = 1;
        if(section != null){
            index = section.getKeys(false).size();
            index++;
        }

        SpawnConfigManager.getConfig().set("location.mapspawns." + index, loc);
        SpawnConfigManager.saveConfig();
    }

    public static List<Location> getListOfSpawnpoints() {
        List<Location> list = new ArrayList<>();
        ConfigurationSection section = SpawnConfigManager.getConfig().getConfigurationSection("location.mapspawns");

        if(section == null) return null;
        for(String key : section.getKeys(false)){
            list.add(SpawnConfigManager.getConfig().getLocation("location.mapspawns." + key));
        }

        return list;



    }

    public static Location getLobby(){
        return SpawnConfigManager.getConfig().getLocation("location.lobby");
    }

    public static void teleportQuakePlayersToPoints(List<QuakePlayer> quakePlayerList) {
        int index = 0;
        for (int i = 0; i < quakePlayerList.size(); i++) {
            quakePlayerList.get(i).teleport(getListOfSpawnpoints().get(index));
            index++;
            if (index >= getListOfSpawnpoints().size()) index = 0;
        }
    }
    public static Location getRandomSpawnPoint(){

        Random rand = new Random();
        int n = rand.nextInt(getListOfSpawnpoints().size());

        return getListOfSpawnpoints().get(n);

    }

}



