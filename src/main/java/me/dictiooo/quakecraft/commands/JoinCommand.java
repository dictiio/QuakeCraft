package me.dictiooo.quakecraft.commands;

import me.dictiooo.quakecraft.QuakeCraft;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class JoinCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage("You cannot join the game!");
            return true;
        }
        Player player = (Player) sender;
        QuakeCraft.getInstance().getGame().getPlayerManager().addPlayer(player);
        return true;
    }
}
