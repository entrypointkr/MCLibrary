package kr.rvs.mclibrary.util.bukkit.inventory.handler;

import kr.rvs.mclibrary.util.bukkit.inventory.GUIHandler;
import kr.rvs.mclibrary.util.bukkit.inventory.InventoryUtils;
import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * Created by Junhyeong Lim on 2017-08-18.
 */
public abstract class TopInventoryHandler implements GUIHandler {
    @Override
    public void onClick(InventoryClickEvent e) {
        if (InventoryUtils.movedSlot(e) < e.getInventory().getSize()) {
            receive(e);
        }
    }

    public abstract void receive(InventoryClickEvent e);
}
