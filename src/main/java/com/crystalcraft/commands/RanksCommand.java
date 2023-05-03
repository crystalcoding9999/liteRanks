package com.crystalcraft.commands;

import com.crystalcraft.classes.Rank;
import com.crystalcraft.literanks.Core;
import com.crystalcraft.literanks.Main;
import com.crystalcraft.managers.RankManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class RanksCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {

        if (sender instanceof Player && !sender.hasPermission("ranks")) {
            Core.noPermission(sender);
            return false;
        }

        if (args.length == 0) {
            Core.message(Main.prefix + "These are the commands for &b&l/ranks", sender);
            Core.message(Main.prefix + "/ranks create - create a new rank", sender);
            Core.message(Main.prefix + "/ranks delete - delete a rank", sender);
            Core.message(Main.prefix + "/ranks getranks - lists all the ranks", sender);
            Core.message(Main.prefix + "/ranks setrank - set a player rank", sender);
            Core.message(Main.prefix + "/ranks setprefix - set a rank prefix", sender);
            Core.message(Main.prefix + "/ranks setdefaultrank - set the default rank", sender);
            //Core.message(Main.prefix + "/ranks ", sender);
        } else {
            switch (args[0].toLowerCase()) {
                case "create":
                    if (Main.rankManager.rankExists(args[1])) {
                        Main.rankManager.addRank(Main.rankManager.createRank(args[1], args[1], new ArrayList<>()));
                        Core.message(Main.prefix + "succesfully created the " + args[1] + " rank", sender);
                    } else {
                        Core.message(Main.prefix + "&cthe rank " + "&f" + args[1] + " &c already exists", sender);
                    }
                    return false;
                case "getranks":
                    for (Rank r : Main.rankManager.ranks) {
                        Core.message(Main.prefix + r.getPrefix() + " " + r.getName(), sender);
                    }
                    return false;
                case "setrank":
                    if (args.length >= 5 || args.length <= 2) {
                        Core.message(Main.prefix + "&cInvalid usage /Ranks setrank <player> <rank> ?-s?", sender);
                        return false;
                    }
                    Player p = Bukkit.getPlayer(args[1]);
                    Rank r = Main.rankManager.findRank(args[2]);
                    if (r != null) {
                        Main.rankManager.setRank(p.getUniqueId(), r);
                        Core.message(Main.prefix + "Succesfully set " + p.getName() + "'s rank to " + r.getName(), sender);
                        if (args.length == 4) {
                            if (args[3] != "-s") {
                                Core.message(Main.prefix + "your rank has been set to " + r.getName(), p);
                            }
                        } else {
                            Core.message(Main.prefix + "your rank has been set to " + r.getName(), p);
                        }
                        String dName = r.getPrefix() + " " + p.getName();
                        Main.playerDNames.put(p.getUniqueId(), dName);
                        p.setPlayerListName(Core.color(dName));
                    } else {
                        Core.message(Main.prefix + "&cThe &f" + args[2] + "&crank doesn't exist", sender);
                    }
                    return false;
                default:
                    Core.message(Main.prefix + "These are the commands for &b&l/ranks", sender);
                    Core.message(Main.prefix + "/ranks create - create a new rank", sender);
                    Core.message(Main.prefix + "/ranks delete - delete a rank", sender);
                    Core.message(Main.prefix + "/ranks getranks - lists all the ranks", sender);
                    Core.message(Main.prefix + "/ranks setrank - set a player rank", sender);
                    Core.message(Main.prefix + "/ranks setprefix - set a rank prefix", sender);
                    Core.message(Main.prefix + "/ranks setdefaultrank - set the default rank", sender);
                    return false;
            }
        }

        return false;
    }
}
