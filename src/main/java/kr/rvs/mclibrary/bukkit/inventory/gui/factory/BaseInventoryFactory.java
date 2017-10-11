package kr.rvs.mclibrary.bukkit.inventory.gui.factory;

import kr.rvs.mclibrary.bukkit.inventory.gui.GUI;
import kr.rvs.mclibrary.bukkit.inventory.gui.GUISignature;
import kr.rvs.mclibrary.bukkit.inventory.gui.InventoryFactory;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

/**
 * Created by Junhyeong Lim on 2017-09-13.
 */
public class BaseInventoryFactory implements InventoryFactory {
    public static final BaseInventoryFactory INSTANCE = new BaseInventoryFactory();

    @Override
    public Inventory create(GUI gui, HumanEntity viewer) {
        GUISignature signature = gui.getSignature();
        signature.title(titleCaught(signature.getTitle()));
        return signature.getType() == InventoryType.CHEST ?
                Bukkit.createInventory(null, signature.getSize(), signature.getTitle()) :
                Bukkit.createInventory(null, signature.getType(), signature.getTitle());
    }

    public String titleCaught(String title) {
        return title; // Hook
    }
}
