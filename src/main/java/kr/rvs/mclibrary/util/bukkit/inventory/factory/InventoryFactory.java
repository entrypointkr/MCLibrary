package kr.rvs.mclibrary.util.bukkit.inventory.factory;

import org.apache.commons.lang.Validate;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

/**
 * Created by Junhyeong Lim on 2017-08-18.
 */
public abstract class InventoryFactory {
    private InventoryType type;
    private String title;
    private int size;
    private Map<Integer, ItemStack> contents;

    public void initialize(InventoryType type, String title, int size, Map<Integer, ItemStack> contents) {
        this.type = type;
        this.title = title;
        this.size = size;
        this.contents = contents;
    }

    public InventoryType getType() {
        validate(type);
        return type;
    }

    public String getTitle() {
        validate(title);
        return title;
    }

    public int getSize() {
        validate(size);
        return size;
    }

    public Map<Integer, ItemStack> getContents() {
        validate(contents);
        return contents;
    }

    private void validate(Object obj) {
        Validate.notNull(obj, "Not initialized yet.");
    }

    public abstract Inventory create();
}
