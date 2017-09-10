package kr.rvs.mclibrary.bukkit.inventory.gui.processor;

import kr.rvs.mclibrary.bukkit.inventory.gui.GUI;
import org.bukkit.inventory.Inventory;

/**
 * Created by Junhyeong Lim on 2017-09-10.
 */
public interface InventoryProcessor {
    void initialize(GUI gui);

    void process(Inventory inv);
}
