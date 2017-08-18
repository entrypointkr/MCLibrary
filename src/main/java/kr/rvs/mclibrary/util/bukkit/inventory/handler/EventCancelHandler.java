package kr.rvs.mclibrary.util.bukkit.inventory.handler;

import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * Created by Junhyeong Lim on 2017-08-18.
 */
public class EventCancelHandler extends TopInventoryHandler {
    @Override
    public void receive(InventoryClickEvent e) {
        e.setCancelled(true);
    }
}
