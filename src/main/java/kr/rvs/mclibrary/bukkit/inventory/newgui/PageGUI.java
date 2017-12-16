package kr.rvs.mclibrary.bukkit.inventory.newgui;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;

/**
 * Created by Junhyeong Lim on 2017-12-11.
 */
public class PageGUI extends GUI { // TODO
    public PageGUI(GUIData data) {
        super(data);
    }

    @Override
    protected void onOpen(InventoryOpenEvent event) {
        getHandlers().notify(event);
    }

    @Override
    protected void onClose(InventoryCloseEvent event) {
        getHandlers().notify(event);
    }

    @Override
    protected void onClick(InventoryClickEvent event) {
        getHandlers().notify(event);
    }

    @Override
    protected void onDrag(InventoryDragEvent event) {
        getHandlers().notify(event);
    }

    @Override
    protected Inventory createInventory() {
        return getData().createInventory();
    }
}
