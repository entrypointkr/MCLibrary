package kr.rvs.mclibrary.util.bukkit.inventory.gui;

import kr.rvs.mclibrary.util.bukkit.inventory.event.GUIClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;

/**
 * Created by Junhyeong Lim on 2017-09-10.
 */
public interface GUIHandler {
    default void onClick(GUIClickEvent e) {
        // Hook
    }

    default void onDrag(InventoryDragEvent e) {
        // Hook
    }

    default void onClose(InventoryCloseEvent e) {
        // Hook
    }
}
