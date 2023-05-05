package com.crystalcraft.listeners;

import com.crystalcraft.classes.Rank;
import com.crystalcraft.literanks.Core;
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
        e.setJoinMessage(null);
        Player p = e.getPlayer();
        Rank r = Main.rankManager.getRank(p.getUniqueId());
        if (r == null) {
            p.kickPlayer("there was an error while loading your rank");
            Core.console("there was an error while loading " + p.getName() + "'s rank");
            return;
        }
        Main.rankManager.updateName(p);
        Main.permissionsManager.updatePlayerPermissions(p);
        String dName = Main.playerDNames.get(p.getUniqueId());
        e.setJoinMessage(Core.color(dName + "&e joined the game"));
    }
}