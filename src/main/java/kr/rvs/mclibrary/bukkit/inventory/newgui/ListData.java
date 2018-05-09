package kr.rvs.mclibrary.bukkit.inventory.newgui;

import kr.rvs.mclibrary.bukkit.Colors;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class ListData implements GUIData {
    private final int size;
    private String title;
    private List<ItemStack> items;

    public static ListData of(int size) {
        return new ListData(size);
    }

    public static ListData ofRow(int row) {
        return of(row * 9);
    }

    private ListData(int size) {
        this.size = size;
        this.title = InventoryType.CHEST.getDefaultTitle();
        this.items = new ArrayList<>(size);
    }

    public ListData title(String title) {
        this.title = Colors.colorize(title);
        return this;
    }

    public ListData addItem(Collection<ItemStack> items) {
        this.items.addAll(items);
        return this;
    }

    public ListData addItem(ItemStack... items) {
        return addItem(Arrays.asList(items));
    }

    public ListData addItem(Iterable<ItemStack> items) {
        items.forEach(this.items::add);
        return this;
    }

    @Override
    public InventoryType type() {
        return InventoryType.CHEST;
    }

    @Override
    public String title() {
        return title;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public ItemStack[] contents() {
        return items.toArray(new ItemStack[items.size()]);
    }
}
