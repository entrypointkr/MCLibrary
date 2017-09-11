package kr.rvs.mclibrary.bukkit.inventory.gui;

import kr.rvs.mclibrary.bukkit.inventory.ItemContents;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

/**
 * Created by Junhyeong Lim on 2017-09-10.
 */
public interface GUISignature {
    InventoryType getType();

    String getTitle();

    int getSize();

    ItemContents getContents();

    default boolean isSimilar(Inventory inv) {
        return inv != null &&
                getType() == inv.getType() &&
                getTitle().equals(inv.getTitle()) &&
                getSize() == inv.getSize();
    }
}
