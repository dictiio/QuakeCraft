package me.dictiooo.quakecraft.utils;

import me.dictiooo.quakecraft.game.Game;
import me.dictiooo.quakecraft.player.QuakePlayer;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class QuakeGun implements Listener {

    private Game game;
    private static ItemStack gun = new ItemBuilder(Material.FIREWORK_ROCKET)
                .setDisplayName("§c§lQuakeCraft Gun").build();

    private HashMap<UUID, Long> gunCooldown = new HashMap<>();

    public QuakeGun(Game game){
        this.game = game;
    }


    public static ItemStack getItem(){
        return gun;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        if(e.getPlayer().getInventory().getItemInMainHand().getType() == Material.AIR) return;
        if(e.getPlayer().getPotionEffect(PotionEffectType.DAMAGE_RESISTANCE) != null) return;
        if(e.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName().equalsIgnoreCase("§c§lQuakeCraft Gun")){
            Player player = e.getPlayer();
            long timeLeft = GlobalVariables.GUN_COOLDOWN;
            if(gunCooldown.containsKey(player.getUniqueId())) {
                timeLeft = System.currentTimeMillis() - gunCooldown.get(player.getUniqueId());
            }
            if(timeLeft >= GlobalVariables.GUN_COOLDOWN){

                Vector dir = player.getEyeLocation().getDirection().normalize();
                Location loc = player.getEyeLocation();
                double t = 0;
                for (int i = 0; i < 40; i++) {
                    t += 0.5;
                    double x = dir.getX() * t;
                    double y = dir.getY() * t;
                    double z = dir.getZ() * t;

                    loc.add(x, y, z);
                    loc.getWorld().spawnParticle(Particle.REDSTONE, loc, 1, new Particle.DustOptions(Color.WHITE, 1));

                }

                Arrow bullet = player.launchProjectile(Arrow.class, player.getEyeLocation().getDirection().multiply(5));
                bullet.setShooter(player);
                bullet.setSilent(true);
                bullet.setGravity(false);
                player.playSound(player.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_LAUNCH, 1, 1);
                gunCooldown.put(player.getUniqueId(), System.currentTimeMillis());
            } else {
                double seconds = Double.valueOf(timeLeft)/1000;
                seconds = GlobalVariables.GUN_COOLDOWN/1000 - seconds;
                DecimalFormat df = new DecimalFormat("###.##");
                player.spigot().sendMessage(
                        ChatMessageType.ACTION_BAR,
                        new TextComponent("§cQuakeGun §7" + df.format(seconds) + "s")
                );

            }

            }
        }
    }




