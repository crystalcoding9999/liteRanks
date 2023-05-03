package com.crystalcraft.literanks;

import com.crystalcraft.commands.*;
import com.crystalcraft.listeners.ChatListener;
import com.crystalcraft.listeners.JoinListener;
import com.crystalcraft.managers.RankManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public final class Main extends JavaPlugin {

    public static Main instance;
    public static String prefix = "&b&lLite&f&lRanks &r» ";

    public static RankManager rankManager;

    public static HashMap<UUID, String> playerDNames = new HashMap<>();

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();

        rankManager = new RankManager();
        rankManager.init();

        new JoinListener(this);
        new ChatListener(this);

        getCommand("ranks").setExecutor(new RanksCommand());
        getCommand("ping").setExecutor(new PingCommand());
    }

    @Override
    public void onDisable() {
        rankManager.saveAndDenit();
    }
}
