package com.crystalcraft.literanks;

import com.crystalcraft.commands.*;
import com.crystalcraft.listeners.ChatListener;
import com.crystalcraft.listeners.GuiListeners;
import com.crystalcraft.listeners.JoinListener;
import com.crystalcraft.managers.PermissionsManager;
import com.crystalcraft.managers.RankManager;
import com.crystalcraft.util.Core;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    public static Main instance;
    public static String prefix = "&b&lLite&f&lRanks &r» ";

    public static RankManager rankManager;
    public static PermissionsManager permissionsManager;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();

        rankManager = new RankManager();
        rankManager.init();

        permissionsManager = new PermissionsManager();

        new JoinListener(this);
        new ChatListener(this);
        new GuiListeners(this);

        getCommand("ranks").setExecutor(new RanksCommand());
        getCommand("ranks").setTabCompleter(new RanksTabCompleter());
        //getCommand("ping").setExecutor(new PingCommand()); // debug command used during development
        //getCommand("test").setExecutor(new TestCommand()); // debug command used during development

        for (Player p : Bukkit.getOnlinePlayers()) {
            rankManager.playerJoin(p);
        }

        Core.console("[liteRanks] Thank you for using my plugin!");
        Core.console("[liteRanks] please consider joining my discord server: https://discord.gg/3vW9dxV686");
    }

    @Override
    public void onDisable() {
        rankManager.saveAndDenit();
    }
}
