package kr.rvs.mclibrary.bukkit.inventory.newgui.handler;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryEvent;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Junhyeong Lim on 2017-12-11.
 */
public class TopHandler implements GUIHandler {
    private final List<GUIHandler<InventoryEvent>> handlers = new ArrayList<>();

    public static boolean isTop(InventoryEvent event, int slot) {
        Inventory topInv = event.getInventory();
        return topInv != null && slot >= 0 && slot <= topInv.getSize();
    }

    @SafeVarargs
    public final TopHandler addHandler(GUIHandler<InventoryEvent>... handlers) {
        this.handlers.addAll(Arrays.asList(handlers));
        return this;
    }

    private void notify(InventoryEvent event) {
        handlers.forEach(handler -> handler.onEvent(event));
    }

    @Override
    public void onEvent(InventoryEvent event) {
        if (event instanceof InventoryClickEvent) {
            InventoryClickEvent clickEvent = ((InventoryClickEvent) event);
            if (isTop(event, clickEvent.getRawSlot())) {
                notify(event);
            }
        } else if (event instanceof InventoryDragEvent) {
            InventoryDragEvent dragEvent = ((InventoryDragEvent) event);
            for (int slot : dragEvent.getRawSlots()) {
                if (isTop(event, slot)) {
                    notify(event);
                    break;
                }
            }
        }
    }
}
