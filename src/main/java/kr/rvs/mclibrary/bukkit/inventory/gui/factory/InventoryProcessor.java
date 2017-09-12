package kr.rvs.mclibrary.bukkit.inventory.gui.factory;

import kr.rvs.mclibrary.bukkit.inventory.gui.GUISignature;
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
    public Inventory create0(GUISignature signature, HumanEntity viewer, InventoryFactory delegate) {
        Inventory baseInv = delegate.create(signature, viewer);
        process(signature, viewer, baseInv);
        return baseInv;
    }

    public abstract void process(GUISignature signature, HumanEntity viewer, Inventory baseInv);
}
