package me.dictiooo.quakecraft.commands;

import me.dictiooo.quakecraft.manager.SpawnpointManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AddSpawnCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player)){
            sender.sendMessage("You cannot set a spawn point!");
            return true;
        }

        Player player = (Player) sender;
        SpawnpointManager.addSpawn(player.getLocation());
        player.sendMessage("Location was added to the list of spawn points.");

        return true;
    }
}
