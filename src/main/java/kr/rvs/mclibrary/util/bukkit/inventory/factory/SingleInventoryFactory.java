package kr.rvs.mclibrary.util.bukkit.inventory.factory;

import org.bukkit.inventory.Inventory;

/**
 * Created by Junhyeong Lim on 2017-08-18.
 */
public class SingleInventoryFactory extends InventoryFactory {
    private final InventoryFactory factory;
    private Inventory cachedInventory;

    public SingleInventoryFactory(InventoryFactory factory) {
        this.factory = factory;
    }

    @Override
    public Inventory create() {
        if (cachedInventory == null) {
            cachedInventory = factory.create();
        }
        return cachedInventory;
    }

    public void clearCache() {
        cachedInventory = null;
    }
}
