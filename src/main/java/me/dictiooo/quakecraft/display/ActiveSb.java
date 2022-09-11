package me.dictiooo.quakecraft.display;

import me.dictiooo.quakecraft.game.Game;
import me.dictiooo.quakecraft.phase.Phase;
import me.dictiooo.quakecraft.player.QuakePlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.*;

import java.util.List;

public class ActiveSb {

    public static Scoreboard getActiveSb(Game game, int seconds){
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getNewScoreboard();

        Objective obj = board.registerNewObjective("activeSb", "dummy", "§8-§eQuakeCraft §a" + formatSeconds(seconds) + "§8-");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);

        List<QuakePlayer> quakePlayerList = game.getPlayerManager().getQuakePlayerList();
        for(QuakePlayer p : quakePlayerList){
            Score score = obj.getScore("§7" + p.getPlayer().getName());
            score.setScore(p.getKills());
        }


        return board;
    }

    private static String formatSeconds(int seconds){
        int minute = seconds / 60;
        int second = seconds % 60;
        String minuteText = String.valueOf(minute);
        String secondText = String.valueOf(second);
        if(second < 10){
            secondText = "0"+secondText;
        }

        return minuteText + ":" + secondText;

    }

}
