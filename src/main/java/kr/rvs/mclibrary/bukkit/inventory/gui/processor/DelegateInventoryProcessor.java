package kr.rvs.mclibrary.bukkit.inventory.gui.processor;

import kr.rvs.mclibrary.bukkit.inventory.gui.GUI;
import kr.rvs.mclibrary.bukkit.inventory.gui.GUISignature;
import kr.rvs.mclibrary.bukkit.inventory.gui.Initializable;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.Inventory;

/**
 * Created by Junhyeong Lim on 2017-09-10.
 */
public abstract class DelegateInventoryProcessor implements InventoryProcessor, Initializable {
    private final InventoryProcessor delegate;

    public DelegateInventoryProcessor(InventoryProcessor delegate) {
        this.delegate = delegate;
    }

    @Override
    public void initialize(GUI gui) {
        if (delegate instanceof Initializable)
            ((Initializable) delegate).initialize(gui);
    }

    @Override
    public void process(HumanEntity viewer, GUISignature signature, Inventory inv) {
        process0(viewer, inv, delegate);
    }

    public abstract void process0(HumanEntity viewer, Inventory inv, InventoryProcessor delegate);
}
