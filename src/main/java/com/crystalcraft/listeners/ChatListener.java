package com.crystalcraft.listeners;

import com.crystalcraft.classes.Rank;
import com.crystalcraft.gui.MainGUI;
import com.crystalcraft.gui.MainGUIEnum;
import com.crystalcraft.literanks.Main;
import com.crystalcraft.util.Core;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

    public ChatListener(Main main) {
        main.getServer().getPluginManager().registerEvents(this, main);
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        event.setCancelled(true);
        if (!MainGUI.chatInput.contains(event.getPlayer())) {
            String name = Main.rankManager.getRank(event.getPlayer().getUniqueId()).getPrefix() + " " + event.getPlayer().getName();
            String message = Main.instance.getConfig().getString("chat");
            message = message.replace("<name>", name).replace("<message>", event.getMessage());
            for (Player p : Bukkit.getOnlinePlayers()) {
                Core.message(message, p);
            }
        } else {
            if (MainGUI.open.get(event.getPlayer()).equals(MainGUIEnum.crn)) {
                MainGUI.chatInput.remove(event.getPlayer());
                Rank rank = MainGUI.managingRank.get(event.getPlayer());
                if (event.getMessage().contains("&") || !event.getMessage().equalsIgnoreCase(ChatColor.stripColor(event.getMessage()))) {
                    Core.message(Main.prefix + "&cColor codes are not supported in rank names!", event.getPlayer());
                    return;
                }
                if (rank != null) {
                    Main.rankManager.changeName(rank, Core.color(event.getMessage()));
                    Core.message(Main.prefix + "successfully changed the rank name to: " + ChatColor.stripColor(event.getMessage()), event.getPlayer());
                } else {
                    Core.message(Main.prefix + "&cthe rank was deleted while you were typing!", event.getPlayer());
                }
            } else if (MainGUI.open.get(event.getPlayer()).equals(MainGUIEnum.crp)) {
                MainGUI.chatInput.remove(event.getPlayer());
                MainGUI.chatInput.remove(event.getPlayer());
                Rank rank = MainGUI.managingRank.get(event.getPlayer());
                if (rank != null) {
                    Main.rankManager.changePrefix(rank, Core.color(event.getMessage()));
                    Core.message(Main.prefix + "successfully changed the rank prefix to: " + event.getMessage(), event.getPlayer());
                } else {
                    Core.message(Main.prefix + "&cthe rank was deleted while you were typing!", event.getPlayer());
                }
            }
        }
    }
}
