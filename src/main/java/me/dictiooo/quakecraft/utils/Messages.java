package me.dictiooo.quakecraft.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class Messages {

    public static void send(Player player, String message, String prefix, Sound sound){

        player.sendMessage(getFinalMessage(message, prefix));
        player.playSound(player.getLocation(), sound, 3, 1);

    }

    public static void send(Player player, String message, String prefix){
        send(player, message, prefix, null);
    }

    public static void send(Player player, String message, Sound sound){

        send(player, message, "", sound);
    }

    public static void broadcast(String message, String prefix, Sound sound){
        Bukkit.broadcastMessage(getFinalMessage(message, prefix));
        for(Player p : Bukkit.getOnlinePlayers()){
            p.playSound(p.getLocation(), sound, 3, 1);
        }
    }

    public static void broadcast(String message, String prefix){
        broadcast(message, prefix, null);
    }

    public static void broadcast(String message){
        broadcast(message, "", null);
    }

    public static String getFinalMessage(String message, String prefix){
        String finalMsg;
        if(!prefix.isEmpty()){
            finalMsg = prefix + ChatColor.RESET + " " + message;
        } else{
            finalMsg = message;
        }
        finalMsg = ChatColor.translateAlternateColorCodes('&', finalMsg);
        return finalMsg;
    }
}
