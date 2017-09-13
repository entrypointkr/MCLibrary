package kr.rvs.mclibrary.bukkit.inventory.gui.factory;

import kr.rvs.mclibrary.bukkit.inventory.gui.GUI;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.Inventory;

/**
 * Created by Junhyeong Lim on 2017-09-12.
 */
public abstract class InventoryProcessor extends DelegateInventoryFactory {
    public InventoryProcessor(InventoryFactory delegate) {
        super(delegate);
    }

    @Override
    public Inventory create0(GUI gui, HumanEntity viewer, InventoryFactory delegate) {
        Inventory baseInv = delegate.create(gui, viewer);
        process(gui, viewer, baseInv);
        return baseInv;
    }

    public abstract void process(GUI gui, HumanEntity viewer, Inventory baseInv);
}
