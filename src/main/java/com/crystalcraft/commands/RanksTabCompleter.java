package com.crystalcraft.commands;

import com.crystalcraft.classes.Rank;
import com.crystalcraft.literanks.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class RanksTabCompleter implements TabCompleter {
    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            completions.add("create");
            completions.add("delete");
            completions.add("getranks");
            completions.add("setrank");
            completions.add("setname");
            completions.add("setprefix");
            completions.add("setdefaultrank");
            completions.add("setparentrank");
            completions.add("delparentrank");
            completions.add("addpermission");
            completions.add("removepermission");
            completions.add("getpermissions");
            completions.add("gui");
        } else if (args.length == 2) {
            switch (args[0]) {
                case "create":
                    completions.add("<name>");
                    return completions;
                case "delete":
                case "setprefix":
                case "setname":
                case "setdefaultrank":
                case "setparentrank":
                case "delparentrank":
                case "addpermission":
                case "removepermission":
                case "getpermissions":
                    for (Rank rank : Main.rankManager.ranks) {
                        completions.add(rank.getName());
                    }
                    return completions;
                case "setrank":
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        completions.add(player.getName());
                    }
                    return completions;
            }
        } else if (args.length == 3) {
            switch (args[0]) {
                case "addpermission":
                    for (Permission perm : Bukkit.getPluginManager().getPermissions()) {
                        completions.add(perm.getName());
                    }
                    return completions;
                case "removepermission":
                    Rank r = Main.rankManager.findRank(args[1]);
                    if (r != null) {
                        for (String s : r.getPermissions()) {
                            completions.add(s);
                        }
                    }
                    return completions;
                case "setrank":
                    for (Rank rank : Main.rankManager.ranks) {
                        completions.add(rank.getName());
                    }
                    return completions;
            }
        }

        return completions;
    }
}
