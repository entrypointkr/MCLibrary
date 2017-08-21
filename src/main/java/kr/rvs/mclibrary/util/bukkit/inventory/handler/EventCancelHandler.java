package kr.rvs.mclibrary.util.bukkit.inventory.handler;

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
