package kr.rvs.mclibrary.bukkit.inventory.gui.handler;

import kr.rvs.mclibrary.bukkit.inventory.gui.GUIEvent;
import kr.rvs.mclibrary.bukkit.inventory.gui.GUIHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryEvent;

/**
 * Created by Junhyeong Lim on 2017-10-06.
 */
public abstract class TopInventoryHandler implements GUIHandler {
    @Override
    public void handle(GUIEvent<InventoryEvent> event) {
        InventoryEvent nativeEvent = event.getEvent();
        if (nativeEvent instanceof InventoryClickEvent) {
            InventoryClickEvent clickEvent = (InventoryClickEvent) nativeEvent;
            if (clickEvent.getRawSlot() < clickEvent.getInventory().getSize())
                receive(event);
        } else if (nativeEvent instanceof InventoryDragEvent) {
            InventoryDragEvent dragEvent = (InventoryDragEvent) nativeEvent;
            for (int slot : dragEvent.getRawSlots()) {
                if (slot < dragEvent.getInventory().getSize()) {
                    receive(event);
                    break;
                }
            }
        }
    }

    public abstract void receive(GUIEvent<InventoryEvent> event);
}
