package kr.rvs.mclibrary.bukkit.inventory.gui.factory;

import kr.rvs.mclibrary.bukkit.inventory.gui.GUI;
import kr.rvs.mclibrary.bukkit.inventory.gui.InventoryFactory;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.Inventory;

/**
 * Created by Junhyeong Lim on 2017-09-13.
 */
public class DefaultInventoryProcessor extends InventoryProcessor {
    public DefaultInventoryProcessor(InventoryFactory baseFactory) {
        super(baseFactory);
    }

    @Override
    public void process(GUI gui, HumanEntity viewer, Inventory baseInv) {
        gui.getSignature().getContents().forEach(baseInv::setItem);
    }
}
