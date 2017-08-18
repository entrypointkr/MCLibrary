package kr.rvs.mclibrary.util.bukkit.inventory;

import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

/**
 * Created by Junhyeong Lim on 2017-08-17.
 */
public abstract class GUIHandler {
    public void onClick(InventoryClickEvent e) {
    }

    public void onClose(InventoryCloseEvent e) {
    }

    protected int correctSlot(InventoryClickEvent e) {
        return e.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY ?
                e.getInventory().firstEmpty() :
                e.getRawSlot();
    }
}
