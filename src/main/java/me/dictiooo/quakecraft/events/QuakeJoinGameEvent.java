package me.dictiooo.quakecraft.events;

import me.dictiooo.quakecraft.player.QuakePlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class QuakeJoinGameEvent extends Event {

    private QuakePlayer quakePlayer;
    private static final HandlerList HANDLERS = new HandlerList();

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public QuakeJoinGameEvent(QuakePlayer quakePlayer){
        this.quakePlayer = quakePlayer;
    }

    public QuakePlayer getQuakePlayer() {
        return quakePlayer;
    }
}
