package kr.rvs.mclibrary.bukkit.inventory.gui;

import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.Inventory;

/**
 * Created by Junhyeong Lim on 2017-10-06.
 */
public interface InventoryFactory {
    default void initialize(GUI gui) {

    }

    Inventory create(GUI gui, HumanEntity viewer);
}
