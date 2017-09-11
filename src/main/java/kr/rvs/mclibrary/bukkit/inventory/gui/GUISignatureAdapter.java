package kr.rvs.mclibrary.bukkit.inventory.gui;

import kr.rvs.mclibrary.bukkit.inventory.ItemContents;
import kr.rvs.mclibrary.general.VarargsParser;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;

/**
 * Created by Junhyeong Lim on 2017-09-09.
 */
public class GUISignatureAdapter implements GUISignature {
    private final InventoryType type;
    private String title;
    private int size;
    private final ItemContents contents = new ItemContents();

    public GUISignatureAdapter(InventoryType type) {
        this.type = type;
        this.title = type.getDefaultTitle();
        this.size = type.getDefaultSize();
    }

    public GUISignatureAdapter title(String title) {
        this.title = title;
        return this;
    }

    public GUISignatureAdapter size(int size) {
        this.size = size;
        return this;
    }

    public GUISignatureAdapter item(Integer index, ItemStack item) {
        this.contents.put(index, item);
        return this;
    }

    public GUISignatureAdapter item(ItemStack item, Integer... indexes) {
        for (Integer index : indexes) {
            item(index, item);
        }
        return this;
    }

    public GUISignatureAdapter item(ItemStack... items) {
        for (int i = 0; i < items.length; i++) {
            item(i, items[i]);
        }
        return this;
    }

    /**
     * This method can use two types of multiple args.
     * For example, contents(1, contents, 2, contents, 10, contents)
     *
     * @param args (int slot, ItemStack contents)...
     */
    public GUISignatureAdapter item(Object... args) {
        VarargsParser parser = new VarargsParser(args);
        parser.parse(section ->
                item(section.<Integer>get(0), section.get(1)));

        return this;
    }

    public GUISignatureAdapter item(Collection<ItemStack> items) {
        int index = 0;
        for (ItemStack item : items) {
            item(index++, item);
        }
        return this;
    }

    @Override
    public InventoryType getType() {
        return type;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public ItemContents getContents() {
        return contents;
    }
}
