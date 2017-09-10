package kr.rvs.mclibrary.bukkit.inventory.gui.factory;

import kr.rvs.mclibrary.bukkit.inventory.gui.GUI;
import kr.rvs.mclibrary.bukkit.inventory.gui.GUISignature;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.Inventory;

/**
 * Created by Junhyeong Lim on 2017-09-09.
 */
public abstract class InventoryFactory {
    private GUI gui;

    public void initialize(GUI gui) {
        this.gui = gui;
    }

    public abstract Inventory create(GUISignature signature, HumanEntity human);
}
