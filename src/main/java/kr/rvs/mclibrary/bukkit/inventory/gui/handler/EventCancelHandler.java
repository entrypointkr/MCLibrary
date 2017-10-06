package kr.rvs.mclibrary.bukkit.inventory.gui.handler;

import kr.rvs.mclibrary.bukkit.inventory.gui.GUIEvent;

/**
 * Created by Junhyeong Lim on 2017-10-06.
 */
public class EventCancelHandler extends TopInventoryHandler {
    @Override
    public void receive(GUIEvent event) {
        event.setCancelled(true);
    }
}
