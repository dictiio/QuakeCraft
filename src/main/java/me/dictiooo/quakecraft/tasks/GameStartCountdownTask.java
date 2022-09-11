package me.dictiooo.quakecraft.tasks;

import me.dictiooo.quakecraft.game.Game;
import me.dictiooo.quakecraft.phase.Phase;
import me.dictiooo.quakecraft.phase.PregamePhase;
import me.dictiooo.quakecraft.utils.GlobalVariables;
import me.dictiooo.quakecraft.utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.scheduler.BukkitRunnable;

public class GameStartCountdownTask extends BukkitRunnable {

    private int t = 6;
    private Game game;

    public GameStartCountdownTask(Game game){
        this.game = game;
    }

    @Override
    public void run() {
        if(game.getCurrentPhase().getType() != Phase.GamePhase.STARTING) {
            this.cancel();
            return;
        }
        if(game.getPlayerManager().getQuakePlayerList().size() < game.getPlayerManager().getMinSlots()){
            Messages.broadcast("&7Someone left as the game was starting!", GlobalVariables.PREFIX, Sound.BLOCK_LEVER_CLICK);
            game.setPhase(new PregamePhase());
            this.cancel();
            return;
        }
        t--;
        if(t <= 0){
            game.nextPhase();
            this.cancel();
            return;
        }

        Messages.broadcast("&7Game starts in &e" + t + " seconds", GlobalVariables.PREFIX, Sound.BLOCK_LEVER_CLICK);

    }
}
