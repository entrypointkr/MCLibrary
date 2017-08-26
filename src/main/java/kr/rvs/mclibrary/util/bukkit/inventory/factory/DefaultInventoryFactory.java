package kr.rvs.mclibrary.util.bukkit.inventory.factory;

import kr.rvs.mclibrary.util.bukkit.inventory.GUI;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

/**
 * Created by Junhyeong Lim on 2017-08-18.
 */
public class DefaultInventoryFactory extends InventoryFactory {
    @Override
    public Inventory create(GUI gui, HumanEntity viewer) {
        Inventory inv = getType() == InventoryType.CHEST ?
                Bukkit.createInventory(null, getSize(), getTitle()) :
                Bukkit.createInventory(null, getType(), getTitle());
        for (int i = 0; i < inv.getSize(); i++) {
            inv.setItem(i, getContents().get(i));
        }
        return inv;
    }
}
