package com.crystalcraft.commands;

import com.crystalcraft.util.Core;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class PingCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {

        StringBuilder s = new StringBuilder();

        for (String _s : args) {
            s.append(_s).append(" ");
        }

        Core.message(s.toString(), sender);

        return false;
    }
}
