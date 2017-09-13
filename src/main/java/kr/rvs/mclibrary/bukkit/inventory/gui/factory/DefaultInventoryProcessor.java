package kr.rvs.mclibrary.bukkit.inventory.gui.factory;

/**
 * Created by Junhyeong Lim on 2017-09-13.
 */
public abstract class DefaultInventoryProcessor extends InventoryProcessor {
    public DefaultInventoryProcessor() {
        super(new DefaultInventoryFactory());
    }
}
