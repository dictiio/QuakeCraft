package me.dictiooo.quakecraft.tasks;

import me.dictiooo.quakecraft.QuakeCraft;
import me.dictiooo.quakecraft.manager.SpawnpointManager;
import me.dictiooo.quakecraft.utils.FireworkUtil;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class EndAnimationTask extends BukkitRunnable {

    private int t = 15;

    @Override
    public void run() {
        if(t <= 0){
            QuakeCraft.getInstance().getGame().nextPhase();
            this.cancel();
            return;
        }

        for(int i = 0; i <= 3; i++){
            Random rand = new Random();
            int n = rand.nextInt(SpawnpointManager.getListOfSpawnpoints().size());
            FireworkUtil.spawnRandomFirework(SpawnpointManager.getListOfSpawnpoints().get(n).clone().add(0, 3, 0), 5);
        }

        t--;
    }
}
