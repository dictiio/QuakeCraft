package me.dictiooo.quakecraft.phase;

import me.dictiooo.quakecraft.QuakeCraft;
import me.dictiooo.quakecraft.display.ActiveSb;
import me.dictiooo.quakecraft.events.QuakeKillEvent;
import me.dictiooo.quakecraft.manager.SpawnpointManager;
import me.dictiooo.quakecraft.player.QuakePlayer;
import me.dictiooo.quakecraft.tasks.GameTimerTask;
import me.dictiooo.quakecraft.tasks.NoMovingCountdownTask;
import me.dictiooo.quakecraft.utils.FireworkUtil;
import me.dictiooo.quakecraft.utils.GlobalVariables;
import me.dictiooo.quakecraft.utils.Messages;
import me.dictiooo.quakecraft.utils.QuakeGun;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.List;

public class ActivePhase extends Phase{

    private List<QuakePlayer> quakePlayerList;
    private NoMovingCountdownTask noMovingTask;
    private QuakeGun quakeGun;
    private GameTimerTask gameTimerTask;

    @Override
    public void onEnable() {
        SpawnpointManager.teleportQuakePlayersToPoints(super.game.getPlayerManager().getQuakePlayerList());
        Messages.broadcast("&7Game starts now!", GlobalVariables.PREFIX, Sound.ENTITY_GENERIC_EXPLODE);
        quakePlayerList = super.game.getPlayerManager().getQuakePlayerList();
        for(QuakePlayer p : quakePlayerList){
            p.getPlayer().setScoreboard(ActiveSb.getActiveSb(super.game, GlobalVariables.GAME_TIMER_SECONDS));
            p.drawSpawnAnimation(p.getPlayer().getLocation(), 1);
        }
        Messages.broadcast("&7Shoot other players using the Quake Gun!", GlobalVariables.PREFIX);
        Messages.broadcast("&7First player to reach &e" + game.getScoreToWin() + " kills &7wins!", GlobalVariables.PREFIX);
        this.quakeGun = new QuakeGun(super.game);
        QuakeCraft.getInstance().getServer().getPluginManager().registerEvents(quakeGun, QuakeCraft.getInstance());
        this.noMovingTask = new NoMovingCountdownTask(super.game);
        noMovingTask.runTaskTimer(QuakeCraft.getInstance(), 0, 20);
        this.gameTimerTask = new GameTimerTask(super.game);
        gameTimerTask.runTaskTimer(QuakeCraft.getInstance(), 100, 20);
    }

    @Override
    public void onDisable() {
        HandlerList.unregisterAll(quakeGun);
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e){
        if(!(e.getEntity() instanceof Player)) return;
        if(e.getDamager() instanceof Arrow){
            Arrow arrow = (Arrow) e.getDamager();
            if(arrow.getShooter() instanceof Player){
                e.setCancelled(true);
                Player attacker = (Player) arrow.getShooter();
                Player victim = (Player) e.getEntity();
                if(attacker == victim) return;
                if(victim.getPotionEffect(PotionEffectType.DAMAGE_RESISTANCE) != null) return;
                if(game.getPlayerManager().getUuidToQuakePlayer().containsKey(victim.getUniqueId())){
                    QuakePlayer quakePlayer = game.getPlayerManager().getUuidToQuakePlayer().get(victim.getUniqueId());
                    quakePlayer.setLastAttacker(attacker);
                    FireworkUtil.spawnRandomFirework(victim.getLocation().add(0, 3, 0), 2);
                    quakePlayer.kill();
                    arrow.remove();
                }
            }
        }
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent e){
        if(e.getEntity() instanceof Arrow){
            e.getEntity().remove();
        }
    }

    @EventHandler
    public void onQuakeKill(QuakeKillEvent e){

        checkIfWon();
    }

    @Override
    public Phase getNextPhase() {
        return new EndingPhase();
    }

    @Override
    public GamePhase getType() {
        return GamePhase.ACTIVE;
    }

    public void checkIfWon(){
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

        if(maxValue >= game.getScoreToWin()){
            Messages.broadcast("&e" + maxKey.getPlayer().getName() + "&7 won!", GlobalVariables.PREFIX, Sound.ENTITY_ENDER_DRAGON_GROWL);
            game.setWinner(maxKey);
            game.nextPhase();
        }
    }
}
