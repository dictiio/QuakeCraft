package me.dictiooo.quakecraft.commands;

import me.dictiooo.quakecraft.manager.SpawnpointManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetLobbyCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player)){
            sender.sendMessage("You cannot set the lobby!");
            return true;
        }

        Player player = (Player) sender;

        SpawnpointManager.setLobby(player.getLocation());
        player.sendMessage("Lobby was saved to your location.");

        return true;
    }
}
