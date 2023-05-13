package com.crystalcraft.util;

import com.crystalcraft.classes.Rank;
import com.crystalcraft.literanks.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.Plugin;

public class GuiCreator {

    static ItemStack frame = ItemCreator.createItem(Material.GRAY_STAINED_GLASS_PANE, " ");

    public static Inventory createUI(Player owner, int size) {
        Inventory inv = Bukkit.createInventory(owner, size);

        for (int i = 0; i < size; i++) {
            inv.setItem(i, frame);
        }

        return inv;
    }

    public static Inventory createUI(Player owner, int size, String title) {
        Inventory inv = Bukkit.createInventory(owner, size, title);

        for (int i = 0; i < size; i++) {
            inv.setItem(i, frame);
        }

        return inv;
    }

    public static Inventory createRankUI(Player owner) {
        int size = 27;
        int amountOfRanks = Main.rankManager.ranks.size();
        if (amountOfRanks <= 9) {
        } else if (amountOfRanks <= 14) {
            size = 36;
        } else if (amountOfRanks <= 21) {
            size = 45;
        } else if (amountOfRanks <= 28) {
            size = 54;
        }
        Inventory inv = GuiCreator.createUI(owner, size, "select rank");

        inv.setItem(0, ItemCreator.createItem(Material.ARROW,"back"));

        int rankIndex = 0;
        for (Rank r : Main.rankManager.ranks) {
            ItemStack rItem = ItemCreator.createRankItem(r);
            if (rankIndex == 0) {
                inv.setItem(10, rItem);
            } else if (rankIndex == 1) {
                inv.setItem(11, rItem);
            } else if (rankIndex == 2) {
                inv.setItem(12, rItem);
            } else if (rankIndex == 3) {
                inv.setItem(13, rItem);
            } else if (rankIndex == 4) {
                inv.setItem(14, rItem);
            } else if (rankIndex == 5) {
                inv.setItem(15, rItem);
            } else if (rankIndex == 6) {
                inv.setItem(16, rItem);
            }else if (rankIndex == 7) {
                inv.setItem(19, rItem);
            } else if (rankIndex == 8) {
                inv.setItem(20, rItem);
            } else if (rankIndex == 9) {
                inv.setItem(21, rItem);
            } else if (rankIndex == 10) {
                inv.setItem(22, rItem);
            } else if (rankIndex == 11) {
                inv.setItem(23, rItem);
            } else if (rankIndex == 12) {
                inv.setItem(24, rItem);
            } else if (rankIndex == 13) {
                inv.setItem(25, rItem);
            }else if (rankIndex == 14) {
                inv.setItem(28, rItem);
            } else if (rankIndex == 15) {
                inv.setItem(29, rItem);
            } else if (rankIndex == 16) {
                inv.setItem(30, rItem);
            } else if (rankIndex == 17) {
                inv.setItem(31, rItem);
            } else if (rankIndex == 18) {
                inv.setItem(32, rItem);
            } else if (rankIndex == 19) {
                inv.setItem(33, rItem);
            } else if (rankIndex == 20) {
                inv.setItem(34, rItem);
            }else if (rankIndex == 21) {
                inv.setItem(37, rItem);
            } else if (rankIndex == 22) {
                inv.setItem(38, rItem);
            } else if (rankIndex == 23) {
                inv.setItem(39, rItem);
            } else if (rankIndex == 24) {
                inv.setItem(40, rItem);
            } else if (rankIndex == 25) {
                inv.setItem(41, rItem);
            } else if (rankIndex == 26) {
                inv.setItem(42, rItem);
            } else if (rankIndex == 27) {
                inv.setItem(43, rItem);
            } else {
                break;
            }
            rankIndex++;
        }

        return inv;
    }

    public static Inventory createPermissionUI(Player owner, Rank rank) {
        int size = 27;
        int amountOfRanks = rank.getPermissions().size();
        if (amountOfRanks <= 9) {
        } else if (amountOfRanks <= 14) {
            size = 36;
        } else if (amountOfRanks <= 21) {
            size = 45;
        } else if (amountOfRanks <= 28) {
            size = 54;
        }
        Inventory inv = GuiCreator.createUI(owner, size, "select permission");

        inv.setItem(0, ItemCreator.createItem(Material.ARROW,"back"));

        int rankIndex = 0;
        for (String p : rank.getPermissions()) {
            ItemStack rItem = ItemCreator.createPermissionItem(p);
            if (rankIndex == 0) {
                inv.setItem(10, rItem);
            } else if (rankIndex == 1) {
                inv.setItem(11, rItem);
            } else if (rankIndex == 2) {
                inv.setItem(12, rItem);
            } else if (rankIndex == 3) {
                inv.setItem(13, rItem);
            } else if (rankIndex == 4) {
                inv.setItem(14, rItem);
            } else if (rankIndex == 5) {
                inv.setItem(15, rItem);
            } else if (rankIndex == 6) {
                inv.setItem(16, rItem);
            }else if (rankIndex == 7) {
                inv.setItem(19, rItem);
            } else if (rankIndex == 8) {
                inv.setItem(20, rItem);
            } else if (rankIndex == 9) {
                inv.setItem(21, rItem);
            } else if (rankIndex == 10) {
                inv.setItem(22, rItem);
            } else if (rankIndex == 11) {
                inv.setItem(23, rItem);
            } else if (rankIndex == 12) {
                inv.setItem(24, rItem);
            } else if (rankIndex == 13) {
                inv.setItem(25, rItem);
            }else if (rankIndex == 14) {
                inv.setItem(28, rItem);
            } else if (rankIndex == 15) {
                inv.setItem(29, rItem);
            } else if (rankIndex == 16) {
                inv.setItem(30, rItem);
            } else if (rankIndex == 17) {
                inv.setItem(31, rItem);
            } else if (rankIndex == 18) {
                inv.setItem(32, rItem);
            } else if (rankIndex == 19) {
                inv.setItem(33, rItem);
            } else if (rankIndex == 20) {
                inv.setItem(34, rItem);
            }else if (rankIndex == 21) {
                inv.setItem(37, rItem);
            } else if (rankIndex == 22) {
                inv.setItem(38, rItem);
            } else if (rankIndex == 23) {
                inv.setItem(39, rItem);
            } else if (rankIndex == 24) {
                inv.setItem(40, rItem);
            } else if (rankIndex == 25) {
                inv.setItem(41, rItem);
            } else if (rankIndex == 26) {
                inv.setItem(42, rItem);
            } else if (rankIndex == 27) {
                inv.setItem(43, rItem);
            } else {
                break;
            }
            rankIndex++;
        }

        return inv;
    }
}
