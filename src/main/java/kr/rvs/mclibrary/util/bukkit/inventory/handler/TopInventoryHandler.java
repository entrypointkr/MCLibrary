package kr.rvs.mclibrary.util.bukkit.inventory.handler;

import kr.rvs.mclibrary.util.bukkit.inventory.GUIHandler;
import kr.rvs.mclibrary.util.bukkit.inventory.InventoryUtils;
import kr.rvs.mclibrary.util.bukkit.inventory.event.GUIClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;

/**
 * Created by Junhyeong Lim on 2017-08-18.
 */
public abstract class TopInventoryHandler implements GUIHandler {
    @Override
    public void onClick(GUIClickEvent e) {
        if (InventoryUtils.movedSlot(e) < e.getInventory().getSize()) {
            receive(e);
        }
    }

    @Override
    public void onDrag(InventoryDragEvent e) {
        for (int slot : e.getRawSlots()) {
            if (slot < e.getInventory().getSize()) {
                receive(e);
                break;
            }
        }
    }

    public abstract void receive(InventoryInteractEvent e);
}
