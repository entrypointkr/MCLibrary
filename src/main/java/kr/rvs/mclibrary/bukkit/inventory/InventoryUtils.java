package kr.rvs.mclibrary.bukkit.inventory;

import org.bukkit.inventory.Inventory;
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
}
