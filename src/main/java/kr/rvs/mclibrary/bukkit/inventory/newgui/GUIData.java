package kr.rvs.mclibrary.bukkit.inventory.newgui;

import kr.rvs.mclibrary.bukkit.MCUtils;
import kr.rvs.mclibrary.bukkit.inventory.InventoryUtils;
import kr.rvs.mclibrary.general.VarargsParser;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Junhyeong Lim on 2017-12-11.
 */
public class GUIData {
    private final InventoryType type;
    private int size;
    private String title;
    private final Map<Integer, ItemStack> contents = new HashMap<>();

    public GUIData(InventoryType type) {
        this.type = type;
        this.size = type.getDefaultSize();
        this.title = type.getDefaultTitle();
    }

    public GUIData() {
        this(InventoryType.CHEST);
    }

    public GUIData size(int size) {
        this.size = size;
        return this;
    }

    public GUIData row(int row) {
        return size(row * 9);
    }

    public GUIData title(String title) {
        this.title = MCUtils.colorize(title);
        return this;
    }

    public GUIData item(Integer index, ItemStack item) {
        this.contents.put(index, item);
        return this;
    }

    public GUIData item(ItemStack item, Integer... indexes) {
        for (Integer index : indexes) {
            item(index, item);
        }
        return this;
    }

    public GUIData item(ItemStack... items) {
        for (int i = 0; i < items.length; i++) {
            item(i, items[i]);
        }
        return this;
    }

    /**
     * This method can use two types of multiple args.
     * For example, data(1, data, 2, data, 10, data)
     *
     * @param args (int slot, ItemStack data)...
     * @return this
     */
    public GUIData item(Object... args) {
        VarargsParser parser = new VarargsParser(args);
        parser.parse(section ->
                item(section.<Integer>get(0), section.get(1)));

        return this;
    }

    public GUIData item(Iterable<ItemStack> items) {
        int index = 0;
        for (ItemStack item : items) {
            item(index++, item);
        }
        return this;
    }

    public void setContentsToInventory(Inventory inventory) {
        InventoryUtils.putAll(inventory, contents);
    }

    public Inventory createInventory() {
        Inventory inv = type == InventoryType.CHEST
                ? Bukkit.createInventory(null, size, title)
                : Bukkit.createInventory(null, type, title);
        setContentsToInventory(inv);
        return inv;
    }

    public InventoryType getType() {
        return type;
    }

    public int getSize() {
        return size;
    }

    public String getTitle() {
        return title;
    }

    public Map<Integer, ItemStack> getContents() {
        return contents;
    }
}
