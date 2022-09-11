package me.dictiooo.quakecraft.phase;

import me.dictiooo.quakecraft.QuakeCraft;
import me.dictiooo.quakecraft.events.QuakeJoinGameEvent;
import me.dictiooo.quakecraft.utils.GlobalVariables;
import me.dictiooo.quakecraft.utils.Messages;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class PregamePhase extends Phase{
    @Override
    public void onEnable() {
        QuakeCraft.getInstance().getServer().getPluginManager().registerEvents(protectionListeners, QuakeCraft.getInstance());
        QuakeCraft.getInstance().getServer().getPluginManager().registerEvents(joinQuitListeners, QuakeCraft.getInstance());
    }

    @Override
    public void onDisable() {

    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e){
        e.setCancelled(true);
    }

    @EventHandler
    public void onJoinGame(QuakeJoinGameEvent e) {
        if(QuakeCraft.getInstance().getGame().getPlayerManager().getQuakePlayerList().size() >= GlobalVariables.MIN_SLOTS){
            QuakeCraft.getInstance().getGame().start();
        }
    }

    @Override
    public Phase getNextPhase() {
        return new StartingPhase();
    }

    @Override
    public GamePhase getType() {
        return GamePhase.PREGAME;
    }


}
