package kr.rvs.mclibrary.bukkit.inventory.gui.processor;

import kr.rvs.mclibrary.bukkit.inventory.gui.GUISignature;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.Inventory;

/**
 * Created by Junhyeong Lim on 2017-09-10.
 */
public class NoOpInventoryProcessor implements InventoryProcessor {
    @Override
    public void process(HumanEntity viewer, GUISignature signature, Inventory inv) {

    }
}
