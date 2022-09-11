package me.dictiooo.quakecraft.listeners;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class ProtectionListeners implements Listener {

    @EventHandler (priority = EventPriority.LOW)
    public void onBlockBreak(BlockBreakEvent e){
        if(e.getPlayer().getGameMode() != GameMode.CREATIVE){
            e.setCancelled(true);
        }

    }

    @EventHandler (priority = EventPriority.LOW)
    public void onBlockPlace(BlockPlaceEvent e){
        if(e.getPlayer().getGameMode() != GameMode.CREATIVE){
            e.setCancelled(true);
        }

    }

    @EventHandler (priority = EventPriority.LOW)
    public void onInteract(PlayerInteractEvent e){
        if(e.getPlayer().getGameMode() != GameMode.CREATIVE){
            if(e.getClickedBlock() == null) return;
            if(e.getClickedBlock().getType().isInteractable()){
                e.setCancelled(true);
            }
        }

    }

    @EventHandler (priority = EventPriority.LOW)
    public void onFoodChange(FoodLevelChangeEvent e){
        e.setCancelled(true);
    }

    @EventHandler (priority = EventPriority.LOW)
    public void onInventoryClick(InventoryClickEvent e){
        if(e.getWhoClicked().getGameMode() != GameMode.CREATIVE){
            e.setCancelled(true);
        }
    }

    @EventHandler (priority = EventPriority.LOW)
    public void onItemDrop(PlayerDropItemEvent e){
        if(e.getPlayer().getGameMode() != GameMode.CREATIVE){
            e.setCancelled(true);
        }
    }

    @EventHandler (priority = EventPriority.LOW)
    public void onDamage(EntityDamageEvent e){
        if(e.getEntity() instanceof Player){
            e.setCancelled(true);
        }
    }

}
