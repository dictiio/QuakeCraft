package me.dictiooo.quakecraft.tasks;

import me.dictiooo.quakecraft.display.ActiveSb;
import me.dictiooo.quakecraft.game.Game;
import me.dictiooo.quakecraft.phase.Phase;
import me.dictiooo.quakecraft.player.QuakePlayer;
import me.dictiooo.quakecraft.utils.GlobalVariables;
import me.dictiooo.quakecraft.utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public class GameTimerTask extends BukkitRunnable {


    private int t = GlobalVariables.GAME_TIMER_SECONDS;
    private Game game;

    public GameTimerTask(Game game) {
        this.game = game;
    }


    @Override
    public void run() {

        if(game.getCurrentPhase().getType() != Phase.GamePhase.ACTIVE){
            this.cancel();
            return;
        }
        if(t <= 0){
            Messages.broadcast("&7You ran out of time! Game ends now!", GlobalVariables.PREFIX);
            HashMap<QuakePlayer, Integer> quakePlayerToScore = game.getPlayerManager().getQuakePlayerToScore();
            QuakePlayer maxKey = null;
            int maxValue = 0;
            for(QuakePlayer i : quakePlayerToScore.keySet())
            {
                if(quakePlayerToScore.get(i) > maxValue)
                {
                    maxKey = i;
                    maxValue = quakePlayerToScore.get(i);
                }
            }

            Messages.broadcast("&e" + maxKey.getPlayer().getName() + "&7 won!", GlobalVariables.PREFIX, Sound.ENTITY_ENDER_DRAGON_GROWL);
            game.setWinner(maxKey);
            game.nextPhase();

            this.cancel();
            return;

        }

        if(t == GlobalVariables.GAME_TIMER_SECONDS / 2){
            Messages.broadcast("&7Game ends in &e" + t + "seconds&7!", GlobalVariables.PREFIX, Sound.ENTITY_EXPERIENCE_ORB_PICKUP);
        }

        t--;
        for(Player p : Bukkit.getOnlinePlayers()){
            p.setScoreboard(ActiveSb.getActiveSb(game, t));
        }

    }
}
