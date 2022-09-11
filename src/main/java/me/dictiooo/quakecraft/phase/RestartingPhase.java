package me.dictiooo.quakecraft.phase;

import me.dictiooo.quakecraft.manager.SpawnpointManager;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

public class RestartingPhase extends Phase{
    @Override
    public void onEnable() {
        HandlerList.unregisterAll(protectionListeners);
        HandlerList.unregisterAll(joinQuitListeners);
        for(Player p : Bukkit.getOnlinePlayers()){
            p.teleport(SpawnpointManager.getLobby());
            p.setGameMode(GameMode.ADVENTURE);
            p.getInventory().clear();
            p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
        }
        game.cleanup();
    }

    @Override
    public void onDisable() {

    }

    @Override
    public Phase getNextPhase() {
        return null;
    }

    @Override
    public GamePhase getType() {
        return GamePhase.RESTARTING;
    }
}
