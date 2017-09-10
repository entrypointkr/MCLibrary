package kr.rvs.mclibrary.bukkit.inventory.gui;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Junhyeong Lim on 2017-09-10.
 */
public class GUIListener implements Listener {
    @EventHandler
    public void onClick(InventoryClickEvent e) {
        GUI.getGuiMap().getOptional(e.getWhoClicked()).ifPresent(gui -> {
            InventoryAction action = e.getAction();
            Inventory inv = e.getInventory();

            if (action == InventoryAction.COLLECT_TO_CURSOR) {
                for (ItemStack item : inv) {
                    if (e.getCursor().isSimilar(item)) {
                        e.setCancelled(true);
                        break;
                    }
                }
            }

            gui.getHandlers().notify(e);
        });
    }

    @EventHandler
    public void onDrag(InventoryDragEvent e) {
        GUI.getGuiMap().getOptional(e.getWhoClicked()).ifPresent(gui -> {
            gui.getHandlers().notify(e);
        });
    }

    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        GUI.getGuiMap().getOptional(e.getPlayer()).ifPresent(gui -> {
            gui.getHandlers().notify(e);
            GUI.getGuiMap().remove(e.getPlayer());
        });
    }
}
