package me.dictiooo.quakecraft.listeners;

import me.dictiooo.quakecraft.QuakeCraft;
import me.dictiooo.quakecraft.game.Game;
import me.dictiooo.quakecraft.manager.SpawnpointManager;
import me.dictiooo.quakecraft.player.QuakePlayer;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinQuitListeners implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        e.setJoinMessage(null);
        e.getPlayer().teleport(SpawnpointManager.getLobby());
        e.getPlayer().getInventory().clear();
        e.getPlayer().setGameMode(GameMode.ADVENTURE);
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e){
        e.setQuitMessage(null);
        if(QuakeCraft.getInstance().getGame().getPlayerManager().getUuidToQuakePlayer().containsKey(e.getPlayer().getUniqueId())){
            QuakePlayer quakePlayer = QuakeCraft.getInstance().getGame().getPlayerManager().getUuidToQuakePlayer().get(e.getPlayer().getUniqueId());
            QuakeCraft.getInstance().getGame().getPlayerManager().removePlayer(quakePlayer);
        }
    }


}
