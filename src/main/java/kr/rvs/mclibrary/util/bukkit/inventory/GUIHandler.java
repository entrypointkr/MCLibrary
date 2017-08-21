package kr.rvs.mclibrary.util.bukkit.inventory;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryEvent;

/**
 * Created by Junhyeong Lim on 2017-08-17.
 */
public interface GUIHandler {
    default void onClick(InventoryClickEvent e) {
    }

    default void onClose(InventoryCloseEvent e) {
    }

    default void onDrag(InventoryDragEvent e) {
    }

    default void onUnknown(InventoryEvent e) {
    }
}
