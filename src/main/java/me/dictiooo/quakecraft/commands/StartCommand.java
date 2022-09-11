package me.dictiooo.quakecraft.commands;

import me.dictiooo.quakecraft.QuakeCraft;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class StartCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        boolean canStart = QuakeCraft.getInstance().getGame().start();
        if (canStart) sender.sendMessage("You have started the game.");
        if (!canStart) sender.sendMessage("Couldn't start the game.");
        return true;
    }
}
