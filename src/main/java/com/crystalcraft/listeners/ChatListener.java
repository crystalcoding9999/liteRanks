package com.crystalcraft.listeners;

import com.crystalcraft.literanks.Core;
import com.crystalcraft.literanks.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

    public ChatListener(Main main) {
        main.getServer().getPluginManager().registerEvents(this, main);
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        String pn = Core.color(Main.playerDNames.get(p.getUniqueId()) + "&r");
        String s = e.getMessage();

        for (Player _p:Bukkit.getOnlinePlayers()) {
            _p.sendMessage("<" + pn+ "> " + s);
        }
    }
}
