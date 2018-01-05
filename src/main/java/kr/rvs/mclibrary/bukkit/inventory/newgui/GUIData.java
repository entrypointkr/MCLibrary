package kr.rvs.mclibrary.bukkit.inventory.newgui;

import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

public interface GUIData {
    InventoryType type();

    String title();

    int size();

    ItemStack[] contents();
}
