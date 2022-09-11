package me.dictiooo.quakecraft.utils;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;

import java.util.Random;

public class FireworkUtil {

    public static void spawnRandomFirework(Location loc, int power){
        Firework fw = (Firework) loc.getWorld().spawnEntity(loc, EntityType.FIREWORK);
        FireworkMeta fwm = fw.getFireworkMeta();


        fwm.setPower(power);
        Random rand = new Random();
        int color = rand.nextInt(16777215);
        fwm.addEffect(FireworkEffect.builder().withColor(Color.fromRGB(color)).build());
        fw.setFireworkMeta(fwm);
        fw.detonate();
    }

    public static void spawnFirework(Location loc, int power, Color color){
        Firework fw = (Firework) loc.getWorld().spawnEntity(loc, EntityType.FIREWORK);
        FireworkMeta fwm = fw.getFireworkMeta();


        fwm.setPower(power);
        fwm.addEffect(FireworkEffect.builder().withColor(color).build());
        fw.setFireworkMeta(fwm);
        fw.detonate();
    }

}
