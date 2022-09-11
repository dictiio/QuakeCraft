package me.dictiooo.quakecraft.phase;

import me.dictiooo.quakecraft.QuakeCraft;
import me.dictiooo.quakecraft.game.Game;
import me.dictiooo.quakecraft.listeners.JoinQuitListeners;
import me.dictiooo.quakecraft.listeners.ProtectionListeners;
import org.bukkit.event.Listener;

public abstract class Phase implements Listener {

    public ProtectionListeners protectionListeners = new ProtectionListeners();

    public Game game = QuakeCraft.getInstance().getGame();
    public JoinQuitListeners joinQuitListeners = new JoinQuitListeners();
    public enum GamePhase{
        PREGAME,
        STARTING,
        ACTIVE,
        ENDING,
        RESTARTING
    }

    public abstract void onEnable();
    public abstract void onDisable();
    public abstract Phase getNextPhase();
    public abstract GamePhase getType();

}
