package com.crystalcraft.managers;

import com.crystalcraft.classes.Rank;
import com.crystalcraft.util.Core;
import com.crystalcraft.literanks.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

import java.util.HashMap;
import java.util.UUID;

public class PermissionsManager {

    HashMap<UUID, PermissionAttachment> attachments = new HashMap<>();

    public void updatePlayerPermissions(Player p, boolean tell) {
        PermissionAttachment attachment = attachments.get(p.getUniqueId());

        if (attachment == null) {
            attachment = p.addAttachment(Main.instance);
            attachments.put(p.getUniqueId(), attachment);
        } else {
            p.removeAttachment(attachment);
            attachment = p.addAttachment(Main.instance);
        }
        Rank r = Main.rankManager.getRank(p.getUniqueId());
        Rank pr = Main.rankManager.findRank(r.getParentRank());

        for (String perm : r.getPermissions()) {
            attachment.setPermission(perm,true);
            Core.console("added " + perm + " to " + p.getName());
        }

        if (pr != null) {
            for (String perm : pr.getPermissions()) {
                attachment.setPermission(perm,true);
                Core.console("added " + perm + " to " + p.getName());
            }
        }

        p.recalculatePermissions();
        attachments.put(p.getUniqueId(), attachment);

        if (tell) Core.message(Main.prefix + "&ayour permissions have been updated", p);
    }

    public void updatePlayersPermissions() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            updatePlayerPermissions(p, (p.isOp() || p.hasPermission("staff")));
        }
    }

    public void updateRankPermissions(Rank r) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            updatePlayerPermissions(p, false);
        }
    }
}
