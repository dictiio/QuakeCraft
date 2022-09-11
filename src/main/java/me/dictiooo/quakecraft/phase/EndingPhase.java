package me.dictiooo.quakecraft.phase;

import me.dictiooo.quakecraft.QuakeCraft;
import me.dictiooo.quakecraft.manager.SpawnpointManager;
import me.dictiooo.quakecraft.player.QuakePlayer;
import me.dictiooo.quakecraft.tasks.EndAnimationTask;

import java.util.List;

public class EndingPhase extends Phase {
    @Override
    public void onEnable() {
        List<QuakePlayer> quakePlayerList = game.getPlayerManager().getQuakePlayerList();
        for(QuakePlayer p : quakePlayerList){
            p.getPlayer().getInventory().clear();
        }

        EndAnimationTask endTask = new EndAnimationTask();
        endTask.runTaskTimer(QuakeCraft.getInstance(), 0, 20);
    }

    @Override
    public void onDisable() {

    }

    @Override
    public Phase getNextPhase() {
        return new RestartingPhase();
    }

    @Override
    public GamePhase getType() {
        return GamePhase.ENDING;
    }
}
