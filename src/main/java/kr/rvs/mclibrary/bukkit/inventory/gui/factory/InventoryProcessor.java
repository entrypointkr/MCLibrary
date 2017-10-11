package kr.rvs.mclibrary.bukkit.inventory.gui.factory;

import kr.rvs.mclibrary.bukkit.inventory.gui.GUI;
import kr.rvs.mclibrary.bukkit.inventory.gui.InventoryFactory;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.Inventory;

/**
 * Created by Junhyeong Lim on 2017-09-13.
 */
public abstract class InventoryProcessor implements InventoryFactory {
    private final InventoryFactory baseFactory;

    public InventoryProcessor(InventoryFactory baseFactory) {
        this.baseFactory = baseFactory;
    }

    public InventoryProcessor() {
        this(BaseInventoryFactory.INSTANCE);
    }

    @Override
    public void initialize(GUI gui) {
        baseFactory.initialize(gui);
    }

    @Override
    public Inventory create(GUI gui, HumanEntity viewer) {
        Inventory inv = baseFactory.create(gui, viewer);
        process(gui, viewer, inv);
        return inv;
    }

    public abstract void process(GUI gui, HumanEntity viewer, Inventory baseInv);
}
