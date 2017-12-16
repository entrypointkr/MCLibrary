package kr.rvs.mclibrary.bukkit.inventory.newgui.handler;

import org.bukkit.event.inventory.InventoryEvent;

/**
 * Created by Junhyeong Lim on 2017-12-11.
 */
public interface GUIHandler<E extends InventoryEvent> {
    void onEvent(E event);
}
