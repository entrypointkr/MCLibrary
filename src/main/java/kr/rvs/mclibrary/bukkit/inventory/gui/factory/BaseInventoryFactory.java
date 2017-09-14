package kr.rvs.mclibrary.bukkit.inventory.gui.factory;

import kr.rvs.mclibrary.bukkit.MCUtils;
import kr.rvs.mclibrary.bukkit.inventory.gui.GUI;
import kr.rvs.mclibrary.bukkit.inventory.gui.GUISignature;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

/**
 * Created by Junhyeong Lim on 2017-09-13.
 */
public class BaseInventoryFactory implements InventoryFactory {
    @Override
    public Inventory create(GUI gui, HumanEntity viewer) {
        GUISignature signature = gui.getSignature();
        String title = titleCaught(gui, signature.getTitle());
        return signature.getType() == InventoryType.CHEST ?
                Bukkit.createInventory(null, signature.getSize(), title) :
                Bukkit.createInventory(null, signature.getType(), title);
    }

    public String titleCaught(GUI gui, String title) {
        return MCUtils.colorize(title); // Hook
    }
}
