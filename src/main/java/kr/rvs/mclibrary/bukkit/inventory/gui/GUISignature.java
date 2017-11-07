package kr.rvs.mclibrary.bukkit.inventory.gui;

import kr.rvs.mclibrary.bukkit.MCUtils;
import kr.rvs.mclibrary.bukkit.inventory.ItemContents;
import kr.rvs.mclibrary.collection.OptionalArrayList;
import kr.rvs.mclibrary.general.VarargsParser;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

/**
 * Created by Junhyeong Lim on 2017-10-06.
 */
public class GUISignature implements Cloneable {
    private InventoryType type = InventoryType.CHEST;
    private String title = type.getDefaultTitle();
    private int size = type.getDefaultSize();
    private ItemContents contents = new ItemContents();
    private OptionalArrayList<Integer> handlerIndexes = new OptionalArrayList<>();

    public GUISignature(InventoryType type) {
        this.type = type;
    }

    public GUISignature() {
    }

    public GUISignature type(InventoryType type) {
        this.type = type;
        return this;
    }

    public GUISignature title(String title) {
        this.title = title;
        return this;
    }

    public GUISignature size(int size) {
        this.size = size;
        return this;
    }

    public GUISignature lineSize(int size) {
        this.size = size * 9;
        return this;
    }

    public GUISignature addHandlerIndexes(Collection<Integer> indexes) {
        this.handlerIndexes.addAll(indexes);
        return this;
    }

    public GUISignature addHandlerIndexes(Integer... indexes) {
        return addHandlerIndexes(Arrays.asList(indexes));
    }

    public GUISignature item(Integer index, ItemStack item) {
        this.contents.put(index, item);
        return this;
    }

    public GUISignature item(ItemStack item, Integer... indexes) {
        for (Integer index : indexes) {
            item(index, item);
        }
        return this;
    }

    public GUISignature item(ItemStack... items) {
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
     * @return this
     */
    public GUISignature item(Object... args) {
        VarargsParser parser = new VarargsParser(args);
        parser.parse(section ->
                item(section.<Integer>get(0), section.get(1)));

        return this;
    }

    public GUISignature item(Collection<ItemStack> items) {
        int index = 0;
        for (ItemStack item : items) {
            item(index++, item);
        }
        return this;
    }

    public GUISignature item(Map<Integer, ItemStack> itemMap) {
        this.contents.putAll(itemMap);
        return this;
    }

    public InventoryType getType() {
        return type;
    }

    public String getTitle() {
        return MCUtils.colorize(title);
    }

    public int getSize() {
        return size;
    }

    public ItemContents getContents() {
        return contents;
    }

    public OptionalArrayList<Integer> getHandlerIndexes() {
        return handlerIndexes;
    }

    public Integer getHandlerIndex(int index, int def) {
        return handlerIndexes.get(index, def);
    }

    public boolean isSimilar(Inventory inv) {
        return inv != null &&
                getType() == inv.getType() &&
                getTitle().equals(inv.getTitle()) &&
                getSize() == inv.getSize();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GUISignature signature = (GUISignature) o;

        if (size != signature.size) return false;
        if (type != signature.type) return false;
        if (title != null ? !title.equals(signature.title) : signature.title != null) return false;
        if (contents != null ? !contents.equals(signature.contents) : signature.contents != null) return false;
        return handlerIndexes != null ? handlerIndexes.equals(signature.handlerIndexes) : signature.handlerIndexes == null;
    }

    @Override
    public int hashCode() {
        int result = type != null ? type.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + size;
        result = 31 * result + (contents != null ? contents.hashCode() : 0);
        result = 31 * result + (handlerIndexes != null ? handlerIndexes.hashCode() : 0);
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public GUISignature clone() {
        try {
            GUISignature signature = (GUISignature) super.clone();
            signature.contents = (ItemContents) contents.clone();
            signature.handlerIndexes = (OptionalArrayList<Integer>) handlerIndexes.clone();
            return signature;
        } catch (CloneNotSupportedException e) {
            throw new IllegalStateException(e);
        }
    }
}
