package me.dictiooo.quakecraft.game;

import me.dictiooo.quakecraft.QuakeCraft;
import me.dictiooo.quakecraft.phase.Phase;
import me.dictiooo.quakecraft.phase.PregamePhase;
import me.dictiooo.quakecraft.manager.PlayerManager;
import me.dictiooo.quakecraft.player.QuakePlayer;
import me.dictiooo.quakecraft.utils.GlobalVariables;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;

import java.util.HashMap;

public class Game {

    private Phase currentPhase;
    private QuakePlayer winner;
    private PlayerManager playerManager;
    private int scoreToWin = GlobalVariables.SCORE_TO_WIN;

    public Game(){
        setPhase(new PregamePhase());
        this.playerManager = new PlayerManager(this);
    }

    // Sets the phase of the game.
    public void setPhase(Phase phase){
        if(currentPhase != null) {
            if (phase != null) {
                currentPhase.onDisable();
            }
            HandlerList.unregisterAll(currentPhase);
        }
        currentPhase = phase;
        phase.onEnable();
        QuakeCraft.getInstance().getServer().getPluginManager().registerEvents(phase, QuakeCraft.getInstance());
    }

    // Sets the phase of the game to the next phase.
    public void nextPhase(){
        setPhase(currentPhase.getNextPhase());
    }

    // Starts the game if it hasn't started.
    public boolean start(){
        if(currentPhase.getType() == Phase.GamePhase.PREGAME) {
            nextPhase();
            if(GlobalVariables.SCORE_TO_WIN_PROPORTIONAL == true){
                double log = Math.log(GlobalVariables.SCORE_TO_WIN) / Math.log(GlobalVariables.MAX_SLOTS);
                scoreToWin = (int) Math.ceil(Math.pow(getPlayerManager().getQuakePlayerList().size(), log));
                if(scoreToWin > GlobalVariables.SCORE_TO_WIN) {
                    scoreToWin = GlobalVariables.SCORE_TO_WIN;
                }
            }

            return true;
        }
        return false;
    }

    // Returns the current phase of the game.
    public Phase getCurrentPhase() {
        return currentPhase;
    }

    // Returns the player manager.
    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    public QuakePlayer getWinner() {
        return winner;
    }

    public void setWinner(QuakePlayer winner) {
        this.winner = winner;
    }

    public int getScoreToWin() {
        return scoreToWin;
    }

    public void cleanup(){
        getPlayerManager().cleanup();
        playerManager = null;
        winner = null;
        setPhase(new PregamePhase());
        playerManager = new PlayerManager(this);
    }
}
