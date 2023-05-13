package com.crystalcraft.listeners;

import com.crystalcraft.classes.Rank;
import com.crystalcraft.gui.MainGUI;
import com.crystalcraft.gui.MainGUIEnum;
import com.crystalcraft.literanks.Main;
import com.crystalcraft.managers.RankManager;
import com.crystalcraft.util.Core;
import com.crystalcraft.util.ItemCreator;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class GuiListeners implements Listener {

    static ArrayList<Player> deleting = new ArrayList<>();

    public GuiListeners(Main main) {
        main.getServer().getPluginManager().registerEvents(this, main);
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if (MainGUI.open.containsKey(p)) {
            e.setCancelled(true);
            if (e.getCurrentItem() == null) return;
            switch (MainGUI.open.get(p)){
                case main:
                    switch (e.getSlot()) {
                        case 11:
                            if (Bukkit.getOnlinePlayers().size() <= 46) MainGUI.mPlayers(p);
                            else Core.message(Main.prefix + "&cThere are to many players online to use this feature",p);
                            return;
                        case 15:
                            MainGUI.mRanks(p);
                            return;
                    }
                    return;
                case mPlayers:
                    if (e.getCurrentItem().getType().equals(Material.PLAYER_HEAD)) {
                        Player player = Bukkit.getPlayer(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()));
                        MainGUI.mPlayer(p,player);
                    } else if (e.getCurrentItem().getType().equals(Material.ARROW)) {
                        MainGUI.main(p);
                    }
                    return;
                case mPlayer:
                    if (e.getCurrentItem().getType().equals(Material.WRITABLE_BOOK)) {
                        Player player = ItemCreator.headToPlayer(e.getInventory().getItem(4));
                        if (player != null) {
                            MainGUI.cpr(p,player);
                        }
                    } else if (e.getCurrentItem().getType().equals(Material.ARROW)) {
                        MainGUI.mPlayers(p);
                    }
                    return;
                case cpr:
                    if (e.getCurrentItem().getType().equals(Material.DIAMOND)) {
                        Player player = ItemCreator.headToPlayer(e.getInventory().getItem(4));
                        Rank r = ItemCreator.itemToRank(e.getCurrentItem());
                        if (player == null) {
                            Core.message(Main.prefix + "&cthis person seems to be offline!", player);
                            return;
                        } else if (r == null) {
                            Core.message(Main.prefix + "&cthis rank seems to have been deleted", player);
                            MainGUI.mPlayer(p,player);
                            return;
                        }

                        Main.rankManager.setRank(player.getUniqueId(),r);
                        Core.message(Main.prefix + "Successfully set " + player.getName() + "'s rank to " + r.getName(), p);
                        MainGUI.mPlayer(p,player);
                    } else if (e.getCurrentItem().getType().equals(Material.ARROW)) {
                        Player player = ItemCreator.headToPlayer(e.getInventory().getItem(4));
                        if (player == null) p.closeInventory();
                        MainGUI.mPlayer(p,player);
                    }
                    return;
                case mRanks:
                    if (e.getCurrentItem().getType().equals(Material.DIAMOND)) {
                        Rank r = ItemCreator.itemToRank(e.getCurrentItem());
                        if (r == null) return;
                        MainGUI.mRank(p,r);
                    } else if (e.getCurrentItem().getType().equals(Material.ARROW)) {
                        MainGUI.main(p);
                    }
                    return;
                case mRank:
                    if (e.getCurrentItem().getType().equals(Material.ARROW)) {
                        MainGUI.mRanks(p);
                        return;
                    } else if (e.getCurrentItem().getType().equals(Material.RED_CONCRETE)) {
                        Rank r = ItemCreator.itemToRank(e.getInventory().getItem(4));
                        if (deleting.contains(p)) {
                            deleting.remove(p);
                            if (Main.rankManager.rankExists(r.getName())) {
                                Core.message(Main.prefix + "&cSuccessfully deleted " + r.getName(),p);
                                Main.rankManager.deleteRank(r);
                                MainGUI.mRanks(p);
                                return;
                            } else {
                                Core.message(Main.prefix + "&cThis rank has already been deleted",p);
                                MainGUI.mRanks(p);
                                return;
                            }
                        } else {
                            deleting.add(p);
                            Core.message(Main.prefix + "&cYou have 10 seconds to confirm deletion",p);
                            new BukkitRunnable(){
                                @Override
                                public void run() {
                                    if (deleting.contains(p)) {
                                        deleting.remove(p);
                                        Core.message(Main.prefix + "&cYour time to confirm deletion has run out", p);
                                    }
                                }
                            }.runTaskLater(Main.instance,200L);
                            return;
                        }
                    } else if (e.getCurrentItem().getType().equals(Material.WRITABLE_BOOK)) {
                        Rank r = ItemCreator.itemToRank(e.getInventory().getItem(4));
                        if (r.getPermissions().isEmpty()) {
                            Core.message(Main.prefix + "&cthis rank doesn't have permissions", p);
                            return;
                        }
                        MainGUI.mrp(p,r);
                        return;
                    } else if (e.getCurrentItem().getType().equals(Material.NAME_TAG)) {
                        Rank r = ItemCreator.itemToRank(e.getInventory().getItem(4));
                        if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("change rank name")) {
                            MainGUI.crn(p,r);
                            return;
                        } else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("change rank prefix")) {
                            MainGUI.crp(p,r);
                            return;
                        }
                    }
                    return;
                case mrp:
                    Rank r = ItemCreator.itemToRank(e.getInventory().getItem(4));
                    if (e.getCurrentItem().getType().equals(Material.ARROW)) {
                        MainGUI.mRank(p,r);
                        return;
                    } else if (e.getCurrentItem().getType().equals(Material.EMERALD)) {
                        if (deleting.contains(p)) {
                            String perm = ItemCreator.itemToPerm(e.getCurrentItem());
                            if (r.getPermissions().contains(perm)) {
                                Main.rankManager.removePermission(perm,r);
                                Core.message(Main.prefix + "&csuccessfully deleted the &f" + perm + " &cfrom &f" + r.getName(),p);
                                deleting.remove(p);
                                MainGUI.mrp(p,r);
                            }
                        } else {
                            deleting.add(p);
                            Core.message(Main.prefix + "&cYou have 10 seconds to confirm deletion",p);
                            new BukkitRunnable(){
                                @Override
                                public void run() {
                                    if (deleting.contains(p)) {
                                        deleting.remove(p);
                                        Core.message(Main.prefix + "&cYour time to confirm deletion has run out", p);
                                    }
                                }
                            }.runTaskLater(Main.instance,200L);
                            return;
                        }
                    }
                default:
                    return;
            }
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        MainGUI.open.remove((Player) e.getPlayer());
        MainGUI.managingRank.remove((Player) e.getPlayer());
        deleting.remove((Player) e.getPlayer());
    }
}
