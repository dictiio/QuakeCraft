package me.dictiooo.quakecraft.tasks;

import me.dictiooo.quakecraft.QuakeCraft;
import me.dictiooo.quakecraft.game.Game;
import me.dictiooo.quakecraft.phase.Phase;
import me.dictiooo.quakecraft.player.QuakePlayer;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class NoMovingCountdownTask extends BukkitRunnable implements Listener {

    private int t = 6;
    private Game game;

    public NoMovingCountdownTask(Game game){

        this.game = game;
        QuakeCraft.getInstance().getServer().getPluginManager().registerEvents(this, QuakeCraft.getInstance());
    }

    @Override
    public void run() {
        if(game.getCurrentPhase().getType() != Phase.GamePhase.ACTIVE) {
            HandlerList.unregisterAll(this);
            this.cancel();
            return;
        }
        t--;
        if(t <= 0){
            for(QuakePlayer p : game.getPlayerManager().getQuakePlayerList()){
                p.getPlayer().sendTitle("§a", "§eGo!", 1, 40, 1);
                p.getPlayer().playSound(p.getPlayer().getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1, 1);
                p.giveKit();
            }
            HandlerList.unregisterAll(this);
            this.cancel();
            return;
        }
        for(QuakePlayer p : game.getPlayerManager().getQuakePlayerList()){
            p.getPlayer().sendTitle("§a", "§e" + t + "...", 1, 40, 1);
            p.getPlayer().playSound(p.getPlayer().getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
        }



    }

    @EventHandler
    public void onMove(PlayerMoveEvent e){
        if(game.getPlayerManager().getUuidToQuakePlayer().containsKey(e.getPlayer().getUniqueId())){
            e.setCancelled(true);
        }
    }
}
