package kr.rvs.mclibrary.util.bukkit.inventory.factory;

import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.Inventory;

/**
 * Created by Junhyeong Lim on 2017-08-18.
 */
public class DefaultInventoryFactory extends InventoryFactory {
    @Override
    public Inventory create(HumanEntity viewer) {
        Inventory inv = createDefaultInventory();
        for (int i = 0; i < inv.getSize(); i++) {
            inv.setItem(i, getContents().get(i));
        }
        return inv;
    }
}
