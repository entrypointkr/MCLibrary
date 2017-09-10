package kr.rvs.mclibrary.bukkit.inventory.gui;

import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

/**
 * Created by Junhyeong Lim on 2017-09-10.
 */
public interface GUISignature {
    InventoryType getType();

    String getTitle();

    int getSize();

    Map<Integer, ItemStack> getContents();

    default boolean isSimilar(Inventory inv) {
        return inv != null &&
                getType() == inv.getType() &&
                getTitle().equals(inv.getTitle()) &&
                getSize() == inv.getSize();
    }
}
