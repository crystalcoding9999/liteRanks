package com.crystalcraft.listeners;

import com.crystalcraft.literanks.Core;
import com.crystalcraft.literanks.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

    public ChatListener(Main main) {
        main.getServer().getPluginManager().registerEvents(this, main);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        event.setFormat(Core.color("%s") + ": " + "%s");
    }
}
