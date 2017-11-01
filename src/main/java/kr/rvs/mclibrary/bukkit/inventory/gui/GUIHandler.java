package kr.rvs.mclibrary.bukkit.inventory.gui;

import org.bukkit.event.inventory.InventoryEvent;

/**
 * Created by Junhyeong Lim on 2017-10-06.
 */
public interface GUIHandler {
    void handle(GUIEvent<InventoryEvent> event); // TODO: Rewrite #GUIEvent
}
