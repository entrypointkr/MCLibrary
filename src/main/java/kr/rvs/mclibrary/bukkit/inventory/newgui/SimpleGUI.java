package kr.rvs.mclibrary.bukkit.inventory.newgui;

import org.bukkit.Bukkit;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.Inventory;

/**
 * Created by Junhyeong Lim on 2017-12-11.
 */
public class SimpleGUI extends GUI {
    public SimpleGUI(GUIData data) {
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
        GUIData data = getData();
        Inventory inv = data.type() == InventoryType.CHEST
                ? Bukkit.createInventory(null, data.size(), data.title())
                : Bukkit.createInventory(null, data.type(), data.title());
        inv.setContents(data.contents());
        return inv;
    }
}
