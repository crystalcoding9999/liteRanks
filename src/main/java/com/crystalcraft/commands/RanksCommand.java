package com.crystalcraft.commands;

import com.crystalcraft.classes.Rank;
import com.crystalcraft.gui.MainGUI;
import com.crystalcraft.gui.MainGUIEnum;
import com.crystalcraft.util.Core;
import com.crystalcraft.literanks.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;

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
                    create(sender, args);
                    return false;
                case "delete":
                    delete(sender, args);
                    return false;
                case "getranks":
                    getranks(sender, args);
                    return false;
                case "setrank":
                    setrank(sender, args);
                    return false;
                case "setname":
                    setName(sender,args);
                    return false;
                case "setprefix":
                    setprefix(sender, args);
                    return false;
                case "setdefaultrank":
                    setdefaultrank(sender, args);
                    return false;
                case "setparentrank":
                    setparentrank(sender, args);
                    return false;
                case "delparentrank":
                    delparentrank(sender, args);
                    return false;
                case "addpermission":
                    addpermission(sender, args);
                    return false;
                case "removepermission":
                    removepermission(sender, args);
                    return false;
                case "getpermissions":
                    getpermissions(sender, args);
                    return false;
                case "gui":
                    if (sender instanceof Player) {
                        MainGUI.main((Player) sender);
                    } else {
                        Core.message(Main.prefix + "&cOnly players can do this", sender);
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
        Core.message(Main.prefix + "/ranks setname - set a rank name", sender);
        Core.message(Main.prefix + "/ranks setprefix - set a rank prefix", sender);
        Core.message(Main.prefix + "/ranks setdefaultrank - set the default rank", sender);
        Core.message(Main.prefix + "/ranks setparentrank - set a ranks parent", sender);
        Core.message(Main.prefix + "/ranks delparentrank - remove a ranks parent", sender);
        Core.message(Main.prefix + "/ranks addpermission - add a rank permission", sender);
        Core.message(Main.prefix + "/ranks removepermission - remove a rank permission", sender);
        Core.message(Main.prefix + "/ranks getpermissions - get a ranks permissions", sender);
        Core.message(Main.prefix + "/ranks gui - open the gui", sender);
        //Core.message(Main.prefix + "/ranks ", sender);
    }

    void create(CommandSender sender, String[] args) {
        if (args.length != 2) {
            Core.message(Main.prefix + "Invalid usage /ranks create <name>", sender);
            return;
        }

        if (Main.rankManager.ranks.size() < 28) {
            if (!Main.rankManager.rankExists(args[1])) {
                Main.rankManager.addRank(Main.rankManager.createRank(args[1], args[1], new ArrayList<>()));
                Core.message(Main.prefix + "succesfully created the " + args[1] + " rank", sender);
            } else {
                Core.message(Main.prefix + "&cthe rank &f" + args[1] + "&c already exists", sender);
            }
        } else {
            Core.message(Main.prefix + "&cYou have reached the max amount of ranks!", sender);
            Core.message(Main.prefix + "&cThis cannot be overriden due to a high likelyhood of plugin failure!", sender);
        }
    }

    void delete(CommandSender sender, String[] args) {
        if (args.length != 2) {
            Core.message(Main.prefix + "Invalid usage /ranks delete <name>", sender);
            return;
        }
        Rank dr = Main.rankManager.findRank(args[1]);
        if (dr == null) {
            Core.message(Main.prefix + "&cthe rank &f" + args[1] + " &cdoes not exist", sender);
            return;
        }
        if (dr.getName() == Main.rankManager.defaultRank.getName()) {
            Core.message(Main.prefix + "&cthe default rank cannot be deleted", sender);
            return;
        }
        if (dr != null) {
            Main.rankManager.deleteRank(dr);
            Core.message(Main.prefix + "Succesfully deleted the " + dr.getName() + " rank", sender);
        } else {
            Core.message(Main.prefix + "&cthe rank &f" + args[1] + " &cdoes not exist", sender);
        }
    }

    void getranks(CommandSender sender, String[] args) {
        for (Rank r : Main.rankManager.ranks) {
            Core.message(Main.prefix + r.getPrefix() + " " + r.getName(), sender);
        }
    }

    void setrank(CommandSender sender, String[] args) {
        if (args.length != 3) {
            Core.message(Main.prefix + "&cInvalid usage /Ranks setrank <player> <rank>", sender);
            return;
        }
        Player p = Bukkit.getPlayer(args[1]);
        Rank r = Main.rankManager.findRank(args[2]);
        if (p == null) {
            Core.message(Main.prefix + "&cPlayer not found", sender);
            return;
        }
        if (r != null) {
            Main.rankManager.setRank(p.getUniqueId(), r);
            Core.message(Main.prefix + "Succesfully set " + p.getName() + "'s rank to " + r.getName(), sender);
        } else {
            Core.message(Main.prefix + "&cThe &f" + args[2] + "&c rank doesn't exist", sender);
        }
    }

    void setName(CommandSender sender, String[] args) {
        if (args.length != 3) {
            Core.message(Main.prefix + "&cInvalid Usage /ranks setname <rank> <name>", sender);
            return;
        }
        Rank spr = Main.rankManager.findRank(args[1]);
        if (spr == null) {
            Core.message(Main.prefix + "&cThe &f" + args[1] + "&c rank doesn't exist", sender);
            return;
        }
        if (args[2].contains("&")) {
            Core.message(Main.prefix + "&cColor codes are not supported in rank names!", sender);
            return;
        }
        String name = args[2];
        String oldName = spr.getName();
        Main.rankManager.changeName(spr, name);
        Core.message(Main.prefix + "successfully changed the " + oldName + "&r rank's name to " + name, sender);
    }

    void setprefix(CommandSender sender, String[] args) {
        if (args.length != 3) {
            Core.message(Main.prefix + "&cInvalid Usage /ranks setprefix <rank> <prefix>", sender);
            return;
        }
        Rank spr = Main.rankManager.findRank(args[1]);
        if (spr == null) {
            Core.message(Main.prefix + "&cThe &f" + args[2] + "&c rank doesn't exist", sender);
            return;
        }
        String prefix = args[2];
        Main.rankManager.changePrefix(spr, prefix);
        Core.message(Main.prefix + "successfully changed the " + spr.getName() + " rank's prefix to " + prefix, sender);
    }

    void setdefaultrank(CommandSender sender, String[] args) {
        if (args.length != 2) {
            Core.message(Main.prefix + "Invalid usage /ranks setdefaultrank <name>", sender);
            return;
        }
        String newRankName = args[1];
        Rank newRank = Main.rankManager.findRank(newRankName);
        if (newRank != null) {
            Main.rankManager.defaultRank = newRank;
            Core.message(Main.prefix + "Succesfully changed the default rank to " + newRankName, sender);
        } else {
            Core.message(Main.prefix + "&cThe &f" + newRankName + "&c rank doesn't exist", sender);
        }
    }

    void setparentrank(CommandSender sender, String[] args) {
        if (args.length != 3) {
            Core.message(Main.prefix + "&cInvalid usage /Ranks setparentrank <childRank> <parentRank>", sender);
            return;
        }

        Rank childRank = Main.rankManager.findRank(args[1]);
        Rank parentRank = Main.rankManager.findRank(args[2]);

        if (childRank == null) {
            Core.message(Main.prefix + args[1] + " &crank not found", sender);
            return;
        } else if (parentRank == null) {
            Core.message(Main.prefix + args[2] + " &crank not found", sender);
            return;
        } else if (childRank.getName() == parentRank.getName()) {
            Core.message(Main.prefix + "&cyou cant set a ranks parent to itself", sender);
            return;
        }

        childRank.setParentRank(parentRank.getName());
        Core.message(Main.prefix + "successfully changed " + args[1] + "'s parent rank to " + args[2], sender);

        for (Player player : Bukkit.getOnlinePlayers()) {
            if (Main.rankManager.getRank(player.getUniqueId()).getName().equalsIgnoreCase(childRank.getName()))
                Main.permissionsManager.updatePlayerPermissions(player, (player.isOp() || player.hasPermission("staff")));
        }
    }

    void delparentrank(CommandSender sender, String[] args) {
        if (args.length != 2) {
            Core.message(Main.prefix + "&cInvalid usage /Ranks delparentrank <rank>", sender);
            return;
        }

        Rank drank = Main.rankManager.findRank(args[1]);

        if (drank == null) {
            Core.message(Main.prefix + args[1] + " &crank not found", sender);
            return;
        }

        drank.setParentRank("");

        for (Player player : Bukkit.getOnlinePlayers()) {
            if (Main.rankManager.getRank(player.getUniqueId()).getName().equalsIgnoreCase(args[1])) {
                Main.permissionsManager.updatePlayerPermissions(player, (player.isOp() || player.hasPermission("staff")));
            }
        }
    }

    void addpermission(CommandSender sender, String[] args) {
        if (args.length != 3) {
            Core.message(Main.prefix + "&cInvalid usage /Ranks addpermission <rank> <permissions>", sender);
            return;
        }

        Rank ar = Main.rankManager.findRank(args[1]);
        String apermission = args[2];

        if (ar == null) {
            Core.message(Main.prefix + args[1] + " &crank not found", sender);
            return;
        }

        if (ar.getPermissions().contains(apermission)) {
            Core.message(Main.prefix + args[1] + " &calready has the &f" + apermission + " &cpermission", sender);
            return;
        }

        Main.rankManager.addPermission(apermission,ar);
        Core.message(Main.prefix + "&asuccessfully added &f" + apermission + " &ato &f" + args[1], sender);
        if (Bukkit.getPluginManager().getPermission(apermission) == null) Core.message(Main.prefix + "&cthe permission wasn't found so theres a chance that it does not work!", sender);
    }

    void removepermission(CommandSender sender, String[] args) {
        if (args.length != 3) {
            Core.message(Main.prefix + "&cInvalid usage /Ranks removepermission <rank> <permissions>", sender);
            return;
        }

        Rank rank = Main.rankManager.findRank(args[1]);
        String permission = args[2];

        if (rank == null) {
            Core.message(Main.prefix + args[1] + " &crank not found", sender);
            return;
        }

        if (!rank.getPermissions().contains(permission)) {
            Core.message(Main.prefix + args[1] + " &cdoes not have the &f" + permission + " &c permission", sender);
            return;
        }

        Main.rankManager.removePermission(permission,rank);
        Core.message(Main.prefix + "&asuccessfully removed &f" + permission + " &afrom &f" + args[1], sender);
    }

    void getpermissions(CommandSender sender, String[] args) {
        if (args.length != 2) {
            Core.message(Main.prefix + "&cInvalid usage /Ranks getpermissions <rank>", sender);
            return;
        }

        Rank rank1 = Main.rankManager.findRank(args[1]);

        if (rank1 == null) {
            Core.message(Main.prefix + args[1] + " &crank not found", sender);
            return;
        }

        if (rank1.getPermissions().isEmpty() && !rank1.hasParentRank()) {
            Core.message(Main.prefix + args[1] + " &cdoes not have any permissions", sender);
            return;
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
    }
}
