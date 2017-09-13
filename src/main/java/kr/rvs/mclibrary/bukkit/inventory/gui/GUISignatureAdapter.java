package kr.rvs.mclibrary.bukkit.inventory.gui;

import kr.rvs.mclibrary.bukkit.inventory.ItemContents;
import kr.rvs.mclibrary.collection.NullableArrayList;
import kr.rvs.mclibrary.general.VarargsParser;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

/**
 * Created by Junhyeong Lim on 2017-09-09.
 */
public class GUISignatureAdapter implements GUISignature, Cloneable {
    private InventoryType type;
    private String title;
    private int size;
    private ItemContents contents = new ItemContents();
    private NullableArrayList<Integer> handlerIndexes = new NullableArrayList<>();

    public GUISignatureAdapter(InventoryType type) {
        this.type = type;
        this.title = type.getDefaultTitle();
        this.size = type.getDefaultSize();
    }

    public GUISignatureAdapter() {
        this(InventoryType.CHEST);
    }

    public GUISignatureAdapter type(InventoryType type) {
        this.type = type;
        return this;
    }

    public GUISignatureAdapter title(String title) {
        this.title = title;
        return this;
    }

    public GUISignatureAdapter size(int size) {
        this.size = size;
        return this;
    }

    public GUISignatureAdapter lineSize(int size) {
        this.size = size * 9;
        return this;
    }

    public GUISignatureAdapter addHandlerIndexes(Collection<Integer> indexes) {
        this.handlerIndexes.addAll(indexes);
        return this;
    }

    public GUISignatureAdapter addHandlerIndexes(Integer... indexes) {
        return addHandlerIndexes(Arrays.asList(indexes));
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

    public GUISignatureAdapter item(Map<Integer, ItemStack> itemMap) {
        this.contents.putAll(itemMap);
        return this;
    }

    public Integer getHandlerIndex(int index, int def) {
        return handlerIndexes.get(index, def);
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

    @Override
    public NullableArrayList<Integer> getHandlerIndexes() {
        return handlerIndexes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GUISignatureAdapter that = (GUISignatureAdapter) o;

        if (size != that.size) return false;
        if (type != that.type) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (contents != null ? !contents.equals(that.contents) : that.contents != null) return false;
        return handlerIndexes != null ? handlerIndexes.equals(that.handlerIndexes) : that.handlerIndexes == null;
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

    @Override
    public String toString() {
        return "GUISignatureAdapter{" +
                "type=" + type +
                ", title='" + title + '\'' +
                ", size=" + size +
                ", handlerIndexes=" + handlerIndexes +
                '}';
    }

    @SuppressWarnings("unchecked")
    @Override
    public GUISignatureAdapter clone() {
        try {
            GUISignatureAdapter adapter = (GUISignatureAdapter) super.clone();
            adapter.contents = (ItemContents) contents.clone();
            adapter.handlerIndexes = (NullableArrayList<Integer>) handlerIndexes.clone();
            return adapter;
        } catch (CloneNotSupportedException e) {
            throw new IllegalStateException(e);
        }
    }
}
