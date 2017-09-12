package kr.rvs.mclibrary.bukkit.inventory.gui.factory;

import kr.rvs.mclibrary.bukkit.inventory.gui.GUI;
import kr.rvs.mclibrary.bukkit.inventory.gui.GUISignature;
import kr.rvs.mclibrary.bukkit.inventory.gui.Initializable;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.Inventory;

/**
 * Created by Junhyeong Lim on 2017-09-12.
 */
public abstract class DelegateInventoryFactory implements InventoryFactory, Initializable {
    private final InventoryFactory delegate;

    public DelegateInventoryFactory(InventoryFactory delegate) {
        this.delegate = delegate;
    }

    @Override
    public void initialize(GUI gui) {
        if (delegate instanceof Initializable)
            ((Initializable) delegate).initialize(gui);
    }

    @Override
    public Inventory create(GUISignature signature, HumanEntity human) {
        return create0(signature, human, delegate);
    }

    public abstract Inventory create0(GUISignature signature, HumanEntity human, InventoryFactory delegate);
}
