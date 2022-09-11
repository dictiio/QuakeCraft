package me.dictiooo.quakecraft.events;

import me.dictiooo.quakecraft.player.QuakePlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class QuakeKillEvent extends Event {

    private QuakePlayer attacker;
    private QuakePlayer victim;

    private static final HandlerList HANDLERS = new HandlerList();

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public QuakeKillEvent(QuakePlayer attacker, QuakePlayer victim){
        this.attacker = attacker;
        this.victim = victim;
    }

    public QuakePlayer getAttacker() {
        return attacker;
    }

    public QuakePlayer getVictim() {
        return victim;
    }
}

