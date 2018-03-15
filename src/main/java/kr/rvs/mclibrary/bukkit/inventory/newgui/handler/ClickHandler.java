package kr.rvs.mclibrary.bukkit.inventory.newgui.handler;

import kr.rvs.mclibrary.collection.OptionalHashMap;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Junhyeong Lim on 2017-12-11.
 */
public class ClickHandler implements GUIHandler<InventoryEvent> {
    private final OptionalHashMap<Integer, List<GUIHandler<InventoryClickEvent>>> handlerMap = new OptionalHashMap<>();

    public ClickHandler handler(Integer slot, GUIHandler<InventoryClickEvent> handler) {
        handlerMap.computeIfAbsent(slot, k -> new ArrayList<>()).add(handler);
        return this;
    }

    public ClickHandler handler(GUIHandler<InventoryClickEvent> handler, Integer... slots) {
        for (Integer slot : slots) {
            handler(slot, handler);
        }
        return this;
    }

    private void notify(Integer slot, InventoryClickEvent event) {
        handlerMap.getOptional(slot).ifPresent(handlers -> handlers.forEach(handler -> handler.onEvent(event)));
    }

    private void processClickEvent(InventoryClickEvent event) {
        if (event.getAction() == InventoryAction.COLLECT_TO_CURSOR) {
            ItemStack cursorItem = event.getCursor();
            Inventory topInv = event.getView().getTopInventory();
            for (int i = 0; i < topInv.getSize(); i++) {
                ItemStack item = topInv.getItem(i);
                if (item != null && item.isSimilar(cursorItem)) {
                    event.setCancelled(true);
                    break;
                }
            }
        }
    }

    private void processDragEvent(InventoryDragEvent event) {
        for (Map.Entry<Integer, ItemStack> entry : event.getNewItems().entrySet()) {
            Integer slot = entry.getKey();
            ItemStack item = entry.getValue();
            InventoryClickEvent clickEvent = new InventoryClickEvent(
                    event.getView(),
                    InventoryType.SlotType.OUTSIDE,
                    slot,
                    ClickType.LEFT,
                    InventoryAction.PLACE_ONE
            );
            clickEvent.setCurrentItem(item);
            notify(slot, clickEvent);

            if (clickEvent.isCancelled()) {
                event.setCancelled(true);
            }
        }
    }

    @Override
    public void onEvent(InventoryEvent event) {
        if (event instanceof InventoryClickEvent) {
            InventoryClickEvent clickEvent = ((InventoryClickEvent) event);
            notify(clickEvent.getRawSlot(), clickEvent);
            processClickEvent(clickEvent);
        } else if (event instanceof InventoryDragEvent) {
            InventoryDragEvent dragEvent = ((InventoryDragEvent) event);
            processDragEvent(dragEvent);
        }
    }
}
