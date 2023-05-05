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
import java.util.UUID;

public class RanksCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {

        if (sender instanceof Player && !sender.hasPermission("ranks")) {
            Core.noPermission(sender);
            return false;
        }

        if (args.length == 0) {
            subcommands(sender);
        } else {
            switch (args[0].toLowerCase()) {
                case "create":
                    if (!Main.rankManager.rankExists(args[1])) {
                        Main.rankManager.addRank(Main.rankManager.createRank(args[1], args[1], new ArrayList<>()));
                        Core.message(Main.prefix + "succesfully created the " + args[1] + " rank", sender);
                    } else {
                        Core.message(Main.prefix + "&cthe rank &f" + args[1] + "&c already exists", sender);
                    }
                    return false;
                case "delete":
                    Rank dr = Main.rankManager.findRank(args[1]);
                    if (dr.getName() == Main.rankManager.defaultRank.getName()) {
                        Core.message(Main.prefix + "&cthe default rank cannot be deleted", sender);
                        return false;
                    }
                    if (dr != null) {
                        Main.rankManager.deleteRank(dr);
                        Core.message(Main.prefix + "Succesfully deleted the " + dr.getName() + " rank", sender);
                    } else {
                        Core.message(Main.prefix + "&cthe rank &f" + args[1] + " &cdoes not exist", sender);
                    }
                    return false;
                case "getranks":
                    for (Rank r : Main.rankManager.ranks) {
                        Core.message(Main.prefix + r.getPrefix() + " " + r.getName(), sender);
                    }
                    return false;
                case "setrank":
                    if (args.length != 3) {
                        Core.message(Main.prefix + "&cInvalid usage /Ranks setrank <player> <rank>", sender);
                        return false;
                    }
                    Player p = Bukkit.getPlayer(args[1]);
                    Rank r = Main.rankManager.findRank(args[2]);
                    if (r != null) {
                        Main.rankManager.setRank(p.getUniqueId(), r);
                        Core.message(Main.prefix + "Succesfully set " + p.getName() + "'s rank to " + r.getName(), sender);
                    } else {
                        Core.message(Main.prefix + "&cThe &f" + args[2] + "&c rank doesn't exist", sender);
                    }
                    return false;
                case "setprefix":
                    if (args.length < 3) {
                        Core.message(Main.prefix + "&cInvalid Usage /ranks setprefix <rank> <prefix>", sender);
                        return false;
                    }
                    Rank spr = Main.rankManager.findRank(args[1]);
                    if (spr == null) {
                        Core.message(Main.prefix + "&cThe &f" + args[2] + "&c rank doesn't exist", sender);
                        return false;
                    }
                    String prefix = args[2];
                    Main.rankManager.changePrefix(spr, prefix);
                    Core.message(Main.prefix + "successfully changed the " + spr.getName() + " rank's prefix to " + prefix, sender);
                    return false;
                case "setdefaultrank":
                    String newRankName = args[1];
                    Rank newRank = Main.rankManager.findRank(newRankName);
                    if (newRank != null) {
                        Main.rankManager.defaultRank = newRank;
                        Core.message(Main.prefix + "Succesfully changed the default rank to " + newRankName, sender);
                    } else {
                        Core.message(Main.prefix + "&cThe &f" + newRankName + "&c rank doesn't exist", sender);
                    }
                    return false;
                case "setparentrank":
                    if (args.length != 3) {
                        Core.message(Main.prefix + "&cInvalid usage /Ranks setparentrank <childRank> <parentRank>", sender);
                        return false;
                    }

                    Rank childRank = Main.rankManager.findRank(args[1]);
                    Rank parentRank = Main.rankManager.findRank(args[2]);

                    if (childRank == null) {
                        Core.message(Main.prefix + args[1] + " &crank not found", sender);
                        return false;
                    } else if (parentRank == null) {
                        Core.message(Main.prefix + args[2] + " &crank not found", sender);
                        return false;
                    } else if (childRank.getName() == parentRank.getName()) {
                        Core.message(Main.prefix + "&cyou cant set a ranks parent to itself", sender);
                        return false;
                    }

                    childRank.setParentRank(parentRank.getName());
                    Core.message(Main.prefix + "successfully changed " + args[1] + "'s parent rank to " + args[2], sender);

                    for (Player player : Bukkit.getOnlinePlayers()) {
                        if (Main.rankManager.getRank(player.getUniqueId()).getName().equalsIgnoreCase(childRank.getName()))
                            Main.permissionsManager.updatePlayerPermissions(player);
                    }

                    return false;
                case "delparentrank":
                    if (args.length != 2) {
                        Core.message(Main.prefix + "&cInvalid usage /Ranks delparentrank <rank>", sender);
                        return false;
                    }

                    Rank drank = Main.rankManager.findRank(args[1]);

                    if (drank == null) {
                        Core.message(Main.prefix + args[1] + " &crank not found", sender);
                        return false;
                    }

                    drank.setParentRank("");

                    for (Player player : Bukkit.getOnlinePlayers()) {
                        if (Main.rankManager.getRank(player.getUniqueId()).getName().equalsIgnoreCase(args[1])) {
                            Main.permissionsManager.updatePlayerPermissions(player);
                        }
                    }

                    return false;
                case "addpermission":
                    if (args.length != 3) {
                        Core.message(Main.prefix + "&cInvalid usage /Ranks addpermission <rank> <permissions>", sender);
                        return false;
                    }

                    Rank ar = Main.rankManager.findRank(args[1]);
                    String apermission = args[2];

                    if (ar == null) {
                        Core.message(Main.prefix + args[1] + " &crank not found", sender);
                        return false;
                    }

                    if (ar.getPermissions().contains(apermission)) {
                        Core.message(Main.prefix + args[1] + " &calready has the &f" + apermission + " &cpermission", sender);
                        return false;
                    }

                    ar.getPermissions().add(apermission);
                    Core.message(Main.prefix + "&asuccessfully added &f" + apermission + " &ato &f" + args[1], sender);
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        if (Main.rankManager.getRank(player.getUniqueId()).getName().equalsIgnoreCase(ar.getName()))
                            Main.permissionsManager.updatePlayerPermissions(player);
                    }
                    return false;
                case "removepermission":
                    if (args.length != 3) {
                        Core.message(Main.prefix + "&cInvalid usage /Ranks removepermission <rank> <permissions>", sender);
                        return false;
                    }

                    Rank rank = Main.rankManager.findRank(args[1]);
                    String permission = args[2];

                    if (rank == null) {
                        Core.message(Main.prefix + args[1] + " &crank not found", sender);
                        return false;
                    }

                    if (!rank.getPermissions().contains(permission)) {
                        Core.message(Main.prefix + args[1] + " &cdoes not have the &f" + permission + " &c permission", sender);
                        return false;
                    }

                    rank.getPermissions().remove(permission);
                    Core.message(Main.prefix + "&asuccessfully removed &f" + permission + " &afrom &f" + args[1], sender);
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        if (Main.rankManager.getRank(player.getUniqueId()).getName().equalsIgnoreCase(rank.getName()))
                            Main.permissionsManager.updatePlayerPermissions(player);
                    }
                    return false;
                case "getpermissions":
                    if (args.length != 2) {
                        Core.message(Main.prefix + "&cInvalid usage /Ranks getpermissions <rank>", sender);
                        return false;
                    }

                    Rank rank1 = Main.rankManager.findRank(args[1]);

                    if (rank1 == null) {
                        Core.message(Main.prefix + args[1] + " &crank not found", sender);
                        return false;
                    }

                    if (rank1.getPermissions().isEmpty() && !rank1.hasParentRank()) {
                        Core.message(Main.prefix + args[1] + " &cdoes not have any permissions", sender);
                        return false;
                    }

                    Core.message(Main.prefix + "these are " + args[1] + "'s permissions", sender);

                    for (String perm : rank1.getPermissions()) {
                        Core.message(Main.prefix + perm, sender);
                    }

                    if (rank1.hasParentRank()) {
                        Rank parent = Main.rankManager.findRank(rank1.getParentRank());
                        for (String perm : parent.getPermissions()) {
                            Core.message(Main.prefix + "[inherited from " + rank1.getParentRank() + "] " + perm, sender);
                        }
                    }

                    return false;
                default:
                    subcommands(sender);
                    return false;
            }
        }

        return false;
    }

    void subcommands(CommandSender sender) {
        Core.message(Main.prefix + "These are the commands for &b&l/ranks", sender);
        Core.message(Main.prefix + "/ranks create - create a new rank", sender);
        Core.message(Main.prefix + "/ranks delete - delete a rank", sender);
        Core.message(Main.prefix + "/ranks getranks - lists all the ranks", sender);
        Core.message(Main.prefix + "/ranks setrank - set a player rank", sender);
        Core.message(Main.prefix + "/ranks setprefix - set a rank prefix", sender);
        Core.message(Main.prefix + "/ranks setdefaultrank - set the default rank", sender);
        Core.message(Main.prefix + "/ranks setparentrank - set a ranks parent", sender);
        Core.message(Main.prefix + "/ranks delparentrank - remove a ranks parent", sender);
        Core.message(Main.prefix + "/ranks addpermission - add a rank permission", sender);
        Core.message(Main.prefix + "/ranks removepermission - remove a rank permission", sender);
        Core.message(Main.prefix + "/ranks getpermissions - get a ranks permissions", sender);
        //Core.message(Main.prefix + "/ranks ", sender);
    }
}
