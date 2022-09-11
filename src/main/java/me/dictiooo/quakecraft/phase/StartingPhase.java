package me.dictiooo.quakecraft.phase;

import me.dictiooo.quakecraft.QuakeCraft;
import me.dictiooo.quakecraft.tasks.GameStartCountdownTask;

public class StartingPhase extends PregamePhase {

    private GameStartCountdownTask task = new GameStartCountdownTask(QuakeCraft.getInstance().getGame());
    @Override
    public void onEnable() {
        task.runTaskTimer(QuakeCraft.getInstance(), 0, 20);
    }

    @Override
    public void onDisable() {

    }

    @Override
    public Phase getNextPhase() {
        return new ActivePhase();
    }

    @Override
    public GamePhase getType() {
        return GamePhase.STARTING;
    }
}
