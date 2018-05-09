package kr.rvs.mclibrary.bukkit.inventory.gui;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import kr.rvs.mclibrary.Static;
import kr.rvs.mclibrary.bukkit.Colors;
import kr.rvs.mclibrary.bukkit.gson.ConfigurationSerializableAdapter;
import kr.rvs.mclibrary.bukkit.inventory.ItemContents;
import kr.rvs.mclibrary.collection.OptionalArrayList;
import kr.rvs.mclibrary.general.VarargsParser;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
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

    public GUISignature(GUISignature signature) {
        this(signature.type);
        this.title = signature.title;
        this.size = signature.size;
        this.contents = new ItemContents(signature.contents);
        this.handlerIndexes = new OptionalArrayList<>(signature.handlerIndexes);
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

    public GUISignature row(int size) {
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
     * For example, data(1, data, 2, data, 10, data)
     *
     * @param args (int slot, ItemStack data)...
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
        return Colors.colorize(title);
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

    public static class Adapter extends TypeAdapter<GUISignature> {
        private final TypeAdapter<Map> mapAdapter;
        private final TypeAdapter<Collection> collectionAdapter;

        public Adapter(TypeAdapter<Map> mapAdapter, TypeAdapter<Collection> collectionAdapter) {
            this.mapAdapter = mapAdapter;
            this.collectionAdapter = collectionAdapter;
        }

        private Object value(Map<Integer, Object> contents, ItemStack item, Map<Integer, Integer> hashCodeMap) {
            int itemHash = item.hashCode();
            Object value = item;
            for (Map.Entry<Integer, Object> entry : contents.entrySet()) {
                int hashCode = hashCodeMap.computeIfAbsent(entry.getKey(), k -> entry.getValue().hashCode());
                if (itemHash == hashCode) {
                    value = entry.getKey();
                    break;
                }
            }

            return value;
        }

        @Override
        public void write(JsonWriter out, GUISignature value) throws IOException {
            if (value != null) {
                Map<Integer, Integer> hashCodeMap = new HashMap<>();
                Map<Integer, Object> contents = new HashMap<>();
                Map<Integer, ItemStack> itemMap = value.getContents();
                for (Map.Entry<Integer, ItemStack> entry : itemMap.entrySet()) {
                    contents.put(entry.getKey(), value(contents, entry.getValue(), hashCodeMap));
                }

                out.beginObject();
                out.name("type").value(value.getType().name());
                out.name("title").value(value.getTitle());
                out.name("size").value(value.getSize());
                out.name("data");
                mapAdapter.write(out, contents);
                out.name("handlerIndexes");
                collectionAdapter.write(out, value.getHandlerIndexes());
                out.endObject();
            } else {
                out.nullValue();
            }
        }

        @Override
        public GUISignature read(JsonReader in) throws IOException {
            if (in.peek() != JsonToken.BEGIN_OBJECT)
                return null;

            GUISignature signature = new GUISignature();
            Map<String, Object> contents = new HashMap<>();
            in.beginObject();
            while (in.hasNext()) {
                String name = in.nextName();
                switch (name) {
                    case "type":
                        signature.type(InventoryType.valueOf(in.nextString()));
                        break;
                    case "title":
                        signature.title(in.nextString());
                        break;
                    case "size":
                        signature.size(in.nextInt());
                        break;
                    case "handlerIndexes":
                        for (Number index : (Collection<Number>) collectionAdapter.read(in)) {
                            signature.addHandlerIndexes(index.intValue());
                        }
                        break;
                    case "data":
                        contents = mapAdapter.read(in);
                        break;
                    default:
                        Static.log("Unknown type, " + name);
                }
            }

            for (Map.Entry<String, Object> entry : contents.entrySet()) {
                Integer key = Integer.parseInt(entry.getKey());
                Object value = entry.getValue();
                if (value instanceof Map) {
                    signature.item(key, ConfigurationSerializableAdapter.deserialize((Map<String, Object>) entry.getValue()));
                } else {
                    Number numVal = (Number) value;
                    signature.item(key, signature.getContents().get(numVal.intValue()));
                }
            }
            in.endObject();

            return signature;
        }
    }
}
