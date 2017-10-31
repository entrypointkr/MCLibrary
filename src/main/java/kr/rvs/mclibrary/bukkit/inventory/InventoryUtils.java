package kr.rvs.mclibrary.bukkit.inventory;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Junhyeong Lim on 2017-08-20.
 */
public class InventoryUtils {
    public static Map<Integer, ItemStack> toMap(Inventory inventory) {
        Map<Integer, ItemStack> ret = new HashMap<>();
        for (int i = 0; i < inventory.getSize(); i++) {
            ret.put(i, inventory.getItem(i));
        }
        return ret;
    }

    public static void putAll(Inventory inv, Map<Integer, ItemStack> contents) {
        for (Map.Entry<Integer, ItemStack> entry : contents.entrySet()) {
            inv.setItem(entry.getKey(), entry.getValue());
        }
    }

    public static int hasItems(Inventory inv, ItemStack item) {
        int hasAmount = 0;
        for (ItemStack elemItem : inv) {
            if (elemItem == null || elemItem.getType() == Material.AIR
                    || !elemItem.isSimilar(item))
                continue;
            hasAmount += elemItem.getAmount();
        }
        return hasAmount;
    }

    public static int hasItems(InventoryHolder holder, ItemStack item) {
        return hasItems(holder.getInventory(), item);
    }

    public static boolean hasItem(Inventory inv, ItemStack item, int amount) {
        int hasAmount = 0;
        for (ItemStack elemItem : inv) {
            if (elemItem == null || elemItem.getType() == Material.AIR
                    || !elemItem.isSimilar(item))
                continue;
            hasAmount += elemItem.getAmount();
            if (hasAmount >= amount)
                return true;
        }
        return false;
    }

    public static boolean hasItem(InventoryHolder holder, ItemStack item, int amount) {
        return hasItem(holder.getInventory(), item, amount);
    }

    public static boolean takeItem(Inventory inv, ItemStack item, int takeAmount) {
        Map<Integer, ItemStack> removeItemMap = new HashMap<>();
        for (int i = 0; i < inv.getSize(); i++) {
            ItemStack elemItem = inv.getItem(i);
            if (elemItem == null || elemItem.getType() == Material.AIR
                    || !elemItem.isSimilar(item))
                continue;
            int amount = elemItem.getAmount();
            if (takeAmount > amount) {
                takeAmount -= amount;
                removeItemMap.put(i, elemItem);
            } else {
                if (takeAmount < amount) {
                    elemItem.setAmount(amount - takeAmount);
                    inv.setItem(i, elemItem);
                } else {
                    removeItemMap.put(i, elemItem);
                }
                for (Integer slot : removeItemMap.keySet()) {
                    inv.clear(slot);
                }
                return true;
            }
        }
        return false;
    }

    public static boolean takeItem(InventoryHolder holder, ItemStack item, int takeAmount) {
        return takeItem(holder.getInventory(), item, takeAmount);
    }

    public static boolean hasSpace(Inventory inv, ItemStack item, int amount) {
        int space = 0;
        for (int i = 0; i < inv.getSize(); i++) {
            ItemStack element = inv.getItem(i);
            if (element == null || element.getType() == Material.AIR) {
                space += item.getMaxStackSize();
            } else if (element.isSimilar(item)) {
                space += item.getMaxStackSize() - element.getAmount();
            }
        }
        return space >= amount;
    }

    public static boolean hasSpace(InventoryHolder holder, ItemStack item, int amount) {
        return hasSpace(holder.getInventory(), item, amount);
    }
}
