package com.crystalcraft.util;

import com.crystalcraft.classes.Rank;
import com.crystalcraft.literanks.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;

public class ItemCreator {
    public static ItemStack createItem(Material mat) {
        return new ItemStack(mat);
    }

    public static ItemStack createItem(Material mat, String name) {
        ItemStack item = createItem(mat);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(Core.color(name));
        item.setItemMeta(itemMeta);
        return item;
    }

    public static ItemStack createItem(Material mat, String name, ArrayList<String> lore) {
        ItemStack item = createItem(mat, name);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);
        return item;
    }

    public static ItemStack createItem(Material mat, String name, ArrayList<String> lore, boolean glint) {
        ItemStack item = createItem(mat, name);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setLore(lore);
        if (glint) {
            itemMeta.addEnchant(Enchantment.LUCK,1,true);
            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        item.setItemMeta(itemMeta);
        return item;
    }


    public static ItemStack createHead(Player owner) {
        ArrayList<String> lore = new ArrayList<>();
        lore.add(Core.color("&fop: " + (owner.isOp() ? "&atrue" : "&cfalse")));
        lore.add(Core.color("&frank: " + Main.rankManager.getRank(owner.getUniqueId()).getName()));
        ItemStack item = createItem(Material.PLAYER_HEAD, owner.getName(), lore);
        SkullMeta itemMeta = (SkullMeta) item.getItemMeta();
        itemMeta.setOwningPlayer(owner);
        item.setItemMeta(itemMeta);
        return item;
    }

    public static ItemStack createHead(Player owner, boolean extra) {
        ArrayList<String> lore = new ArrayList<>();
        lore.add(Core.color("&fop: " + (owner.isOp() ? "&atrue" : "&cfalse")));
        lore.add(Core.color("&frank: " + Main.rankManager.getRank(owner.getUniqueId()).getName()));
        ItemStack item = extra ? createItem(Material.PLAYER_HEAD, owner.getName(), lore) : createItem(Material.PLAYER_HEAD, owner.getName());
        SkullMeta itemMeta = (SkullMeta) item.getItemMeta();
        itemMeta.setOwningPlayer(owner);
        item.setItemMeta(itemMeta);
        return item;
    }

    public static Player headToPlayer(ItemStack item) {
        String name = ChatColor.stripColor(item.getItemMeta().getDisplayName());
        return Bukkit.getPlayer(name);
    }

    public static ItemStack createRankItem(Rank r) {
        return ItemCreator.createItem(Material.DIAMOND,r.getName(), new ArrayList<>(), true);
    }

    public static Rank itemToRank(ItemStack item) {
        String name = ChatColor.stripColor(item.getItemMeta().getDisplayName());
        return Main.rankManager.findRank(name);
    }

    public static ItemStack createPluginItem(Plugin p) {
        return ItemCreator.createItem(Material.EMERALD, p.getName(), new ArrayList<>(), true);
    }

    public static Plugin itemToPlugin(ItemStack item) {
        String name = ChatColor.stripColor(item.getItemMeta().getDisplayName());
        return Bukkit.getPluginManager().getPlugin(name);
    }

    public static ItemStack createPermissionItem(String perm) {
        return createItem(Material.EMERALD, perm, new ArrayList<>(), true);
    }

    public static String itemToPerm(ItemStack item) {
        return ChatColor.stripColor(item.getItemMeta().getDisplayName());
    }
}
