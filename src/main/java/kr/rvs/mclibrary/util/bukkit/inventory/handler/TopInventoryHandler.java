package kr.rvs.mclibrary.util.bukkit.inventory.handler;

import kr.rvs.mclibrary.util.bukkit.inventory.GUIHandler;
import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * Created by Junhyeong Lim on 2017-08-18.
 */
public abstract class TopInventoryHandler extends GUIHandler {
    @Override
    public void onClick(InventoryClickEvent e) {
        if (correctSlot(e) < e.getInventory().getSize()) {
            receive(e);
        }
    }

    public abstract void receive(InventoryClickEvent e);
}
