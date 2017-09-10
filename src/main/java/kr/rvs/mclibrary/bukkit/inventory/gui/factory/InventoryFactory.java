package kr.rvs.mclibrary.bukkit.inventory.gui.factory;

import kr.rvs.mclibrary.bukkit.inventory.gui.GUISignature;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.Inventory;

/**
 * Created by Junhyeong Lim on 2017-09-09.
 */
public abstract class InventoryFactory {
    public abstract Inventory create(GUISignature signature, HumanEntity human);
}
