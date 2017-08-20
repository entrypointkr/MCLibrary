package kr.rvs.mclibrary.util.bukkit.inventory;

import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * Created by Junhyeong Lim on 2017-08-20.
 */
public class InventoryUtils {
    public static int movedSlot(InventoryClickEvent e) {
        return e.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY ?
                e.getInventory().firstEmpty() :
                e.getRawSlot();
    }
}
