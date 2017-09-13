package kr.rvs.mclibrary.bukkit.inventory.gui.factory;

import kr.rvs.mclibrary.bukkit.inventory.gui.GUI;
import kr.rvs.mclibrary.bukkit.inventory.gui.Initializable;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.Inventory;

/**
 * Created by Junhyeong Lim on 2017-09-13.
 */
public abstract class InventoryProcessor implements InventoryFactory, Initializable {
    private final InventoryFactory baseFactory;

    public InventoryProcessor(InventoryFactory baseFactory) {
        this.baseFactory = baseFactory;
    }

    @Override
    public void initialize(GUI gui) {
        if (baseFactory instanceof Initializable)
            ((Initializable) baseFactory).initialize(gui);
    }

    @Override
    public Inventory create(GUI gui, HumanEntity viewer) {
        Inventory baseInv = baseFactory.create(gui, viewer);
        process(gui, viewer, baseInv);
        return baseInv;
    }

    public abstract void process(GUI gui, HumanEntity viewer, Inventory baseInv);
}
