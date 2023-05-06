package com.crystalcraft.listeners;

import com.crystalcraft.literanks.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    public JoinListener(Main main) {
        main.getServer().getPluginManager().registerEvents(this, main);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Main.rankManager.playerJoin(e.getPlayer());
    }
}
