package kr.rvs.mclibrary.bukkit.inventory.newgui;

import kr.rvs.mclibrary.bukkit.Colors;
import kr.rvs.mclibrary.general.VarargsParser;
import org.apache.commons.lang3.Validate;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

public class SimpleData implements GUIData {
    private final InventoryType type;
    private String title;
    private ItemStack[] contents;

    public static SimpleData of(InventoryType type, int size) {
        return new SimpleData(type, size);
    }

    public static SimpleData ofRow(InventoryType type, int row) {
        return of(type, row * 9);
    }

    private SimpleData(InventoryType type, int size) {
        this.type = type;
        this.contents = new ItemStack[size];
        title(type.getDefaultTitle());
    }

    public SimpleData title(String title) {
        this.title = Colors.colorize(title);
        return this;
    }

    public SimpleData item(Integer index, ItemStack item) {
        Validate.isTrue(index < size(), "Index over");
        this.contents[index] = item;
        return this;
    }

    public SimpleData item(ItemStack item, Integer... indexes) {
        for (Integer index : indexes) {
            item(index, item);
        }
        return this;
    }

    public SimpleData item(ItemStack... items) {
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
    public SimpleData item(Object... args) {
        VarargsParser parser = new VarargsParser(args);
        parser.parse(section ->
                item(section.<Integer>get(0), section.get(1)));

        return this;
    }

    public SimpleData item(Iterable<ItemStack> items) {
        int index = 0;
        for (ItemStack item : items) {
            item(index++, item);
        }
        return this;
    }

    @Override
    public InventoryType type() {
        return type;
    }

    @Override
    public String title() {
        return title;
    }

    @Override
    public int size() {
        return contents.length;
    }

    @Override
    public ItemStack[] contents() {
        return contents;
    }

    //    @Override
//    public Inventory createInventory() {
//        Inventory inv = type == InventoryType.CHEST
//                ? Bukkit.createInventory(null, size, title)
//                : Bukkit.createInventory(null, type, title);
//        setContentsToInventory(inv);
//        return inv;
//    }
}
