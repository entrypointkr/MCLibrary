package kr.rvs.mclibrary.bukkit.inventory.gui.handler;

import org.bukkit.event.inventory.InventoryInteractEvent;

/**
 * Created by Junhyeong Lim on 2017-08-18.
 */
public class EventCancelHandler extends TopInventoryHandler {
    @Override
    public void receive(InventoryInteractEvent e) {
        e.setCancelled(true);
    }
}
