package kr.rvs.mclibrary.util.bukkit.inventory.factory;

import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.Inventory;

/**
 * Created by Junhyeong Lim on 2017-08-18.
 */
public class CachedInventoryFactory extends InventoryFactory {
    private final InventoryFactory factory;
    private Inventory cachedInventory;

    public CachedInventoryFactory(InventoryFactory factory) {
        this.factory = factory;
    }

    @Override
    public Inventory create(HumanEntity viewer) {
        if (cachedInventory == null) {
            cachedInventory = factory.create(viewer);
        }
        return cachedInventory;
    }

    public void clearCache() {
        cachedInventory = null;
    }
}
