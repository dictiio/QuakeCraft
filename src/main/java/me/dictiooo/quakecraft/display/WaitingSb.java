package me.dictiooo.quakecraft.display;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

public class WaitingSb {

    public static Scoreboard getWaitingSb(){
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getNewScoreboard();

        Objective obj = board.registerNewObjective("waitingSb", "dummy", "§8-§eQuakeCraft §a0:00§8-");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);

        Score score = obj.getScore("§eWaiting...");
        score.setScore(1);

        return board;
    }

}
