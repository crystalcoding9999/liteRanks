package com.crystalcraft.gui;

import com.crystalcraft.classes.Rank;
import com.crystalcraft.literanks.Main;
import com.crystalcraft.util.Core;
import com.crystalcraft.util.GuiCreator;
import com.crystalcraft.util.ItemCreator;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainGUI {
    public static HashMap<Player, MainGUIEnum> open = new HashMap<>();
    public static HashMap<Player, Rank> managingRank = new HashMap<>();
    public static ArrayList<Player> chatInput = new ArrayList<>();

    public static void main(Player player) {
        player.closeInventory();

        open.put(player, MainGUIEnum.main);
        managingRank.remove(player);

        Inventory inv = GuiCreator.createUI(player, 27, "LiteRanks");

        ItemStack managePlayers = ItemCreator.createItem(Material.PLAYER_HEAD, "&b&lManage players");
        inv.setItem(11, managePlayers);

        ItemStack manageRanks = ItemCreator.createItem(Material.BOOK, "&b&lManage ranks");
        inv.setItem(15, manageRanks);

        player.openInventory(inv);

    }

    public static void mPlayers(Player player) {
        player.closeInventory();
        open.put(player, MainGUIEnum.mPlayers);

        Inventory inv = GuiCreator.createUI(player, 45, "Select player");

        inv.setItem(0,ItemCreator.createItem(Material.ARROW,"back"));

        int pIndex = 1;
        for (Player p : Bukkit.getOnlinePlayers()) {
            inv.setItem(pIndex, ItemCreator.createHead(p, true));
            pIndex++;
        }

        player.openInventory(inv);
    }

    public static void mPlayer(Player opener, Player target) {
        opener.closeInventory();
        open.put(opener, MainGUIEnum.mPlayer);

        Inventory inv = GuiCreator.createUI(opener, 27, "Manage player");

        inv.setItem(0, ItemCreator.createItem(Material.ARROW, "back"));
        inv.setItem(4, ItemCreator.createHead(target,false));

        ArrayList<String> info = new ArrayList<>();
        info.add(Core.color("&frank: " + Main.rankManager.getRank(target.getUniqueId()).getName()));
        info.add(Core.color("&fop: " + (target.isOp() ? "&atrue" : "&cfalse")));
        inv.setItem(14, ItemCreator.createItem(Material.PAPER, "&einformation", info));

        inv.setItem(12, ItemCreator.createItem(Material.WRITABLE_BOOK,"change rank"));

        opener.openInventory(inv);
    }

    public static void cpr(Player opener, Player target) {
        opener.closeInventory();

        open.put(opener, MainGUIEnum.cpr);

        Inventory inv = GuiCreator.createRankUI(opener);

        inv.setItem(0, ItemCreator.createItem(Material.ARROW,"back"));
        inv.setItem(4, ItemCreator.createHead(target,true));

        opener.openInventory(inv);
    }

    public static void mRanks(Player opener) {
        opener.closeInventory();

        open.put(opener, MainGUIEnum.mRanks);
        managingRank.remove(opener);

        Inventory inv = GuiCreator.createRankUI(opener);

        opener.openInventory(inv);
    }

    public static void mRank(Player opener, Rank rank) {
        opener.closeInventory();

        open.put(opener, MainGUIEnum.mRank);
        managingRank.put(opener,rank);

        Inventory inv = GuiCreator.createUI(opener,27,"managing " + rank.getName());

        inv.setItem(0, ItemCreator.createItem(Material.ARROW,"back"));
        inv.setItem(4, ItemCreator.createRankItem(rank));
        ArrayList<String> lore = new ArrayList<>();
        lore.add(Core.color("&cfor deleting permissions only"));
        inv.setItem(10, ItemCreator.createItem(Material.WRITABLE_BOOK,"manage rank permissions", lore));
        inv.setItem(12, ItemCreator.createItem(Material.NAME_TAG,"change rank name"));
        inv.setItem(14, ItemCreator.createItem(Material.NAME_TAG,"change rank prefix"));
        ArrayList<String> delLore = new ArrayList<>();
        delLore.add(Core.color("&fdouble click to delete"));
        inv.setItem(16,ItemCreator.createItem(Material.RED_CONCRETE,"&cDelete", delLore));

        opener.openInventory(inv);
    }

    public static void mrp(Player opener, Rank rank) {
        opener.closeInventory();

        open.put(opener, MainGUIEnum.mrp);
        managingRank.put(opener,rank);

        Inventory inv = GuiCreator.createPermissionUI(opener,rank);

        inv.setItem(4, ItemCreator.createRankItem(rank));

        opener.openInventory(inv);
    }

    public static void crn(Player opener, Rank rank)
    {
        opener.closeInventory();

        open.put(opener, MainGUIEnum.crn);
        managingRank.put(opener, rank);
        chatInput.add(opener);

        Core.message(Main.prefix + "you have 10 seconds to type the new rank name in chat", opener);

        new BukkitRunnable() {
            @Override
            public void run() {
                if (chatInput.contains(opener)) {
                    chatInput.remove(opener);
                    Core.message(Main.prefix + "&cYour time to change the rank name has run out", opener);
                    mRank(opener,rank);
                }
            }
        }.runTaskLaterAsynchronously(Main.instance, 200L);
    }

    public static void crp(Player opener, Rank rank) {
        opener.closeInventory();

        open.put(opener, MainGUIEnum.crp);
        managingRank.put(opener, rank);
        chatInput.add(opener);

        Core.message(Main.prefix + "you have 10 seconds to type the new rank prefix in chat", opener);

        new BukkitRunnable() {
            @Override
            public void run() {
                if (chatInput.contains(opener)) {
                    chatInput.remove(opener);
                    Core.message(Main.prefix + "&cYour time to change the rank prefix has run out", opener);
                    mRank(opener,rank);
                }
            }
        }.runTaskLaterAsynchronously(Main.instance, 200L);
    }
}
