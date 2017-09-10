package kr.rvs.mclibrary.bukkit.inventory.gui.processor;

import kr.rvs.mclibrary.bukkit.inventory.gui.GUI;
import org.bukkit.inventory.Inventory;

/**
 * Created by Junhyeong Lim on 2017-09-10.
 */
public class NoOpInventoryProcessor implements InventoryProcessor {
    @Override
    public void initialize(GUI gui) {
        // NoOp
    }

    @Override
    public void process(Inventory inv) {
        // NoOp
    }
}
