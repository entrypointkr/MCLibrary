package kr.rvs.mclibrary.util.bukkit.inventory.gui.factory;

import kr.rvs.mclibrary.util.bukkit.MCUtils;
import kr.rvs.mclibrary.util.bukkit.inventory.gui.GUISignature;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

/**
 * Created by Junhyeong Lim on 2017-09-09.
 */
public class DefaultInventoryFactory extends InventoryFactory {
    @Override
    public Inventory create(GUISignature signature, HumanEntity human) {
        String title = MCUtils.colorize(signature.getTitle());
        Inventory inv = signature.getType() == InventoryType.CHEST ?
                Bukkit.createInventory(null, signature.getSize(), title) :
                Bukkit.createInventory(null, signature.getType(), title);
        signature.getContents().forEach(inv::setItem);

        return inv;
    }
}
