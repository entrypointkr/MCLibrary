package kr.rvs.mclibrary.bukkit.gson;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import kr.rvs.mclibrary.bukkit.inventory.gui.GUISignature;
import kr.rvs.mclibrary.bukkit.inventory.gui.GUISignatureAdapter;
import kr.rvs.mclibrary.collection.NullableArrayList;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.util.*;

/**
 * Created by Junhyeong Lim on 2017-09-11.
 */
public class GUISignatureTypeAdapter extends TypeAdapter<GUISignature> {
    private final TypeAdapter<Map> mapAdapter;
    private final TypeAdapter<Collection> collectionAdapter;

    public GUISignatureTypeAdapter(TypeAdapter<Map> mapAdapter, TypeAdapter<Collection> collectionAdapter) {
        this.mapAdapter = mapAdapter;
        this.collectionAdapter = collectionAdapter;
    }

    @Override
    public void write(JsonWriter out, GUISignature value) throws IOException {
        Map<Integer, Integer> contents = new HashMap<>();
        List<ItemStack> items = new ArrayList<>();
        for (Map.Entry<Integer, ItemStack> entry : value.getContents().entrySet()) {
            int index = add(items, entry.getValue());
            contents.put(entry.getKey(), index);
        }

        out.beginObject();
        out.name("type").value(value.getType().name());
        out.name("title").value(value.getTitle());
        out.name("size").value(value.getSize());
        out.name("contents");
        mapAdapter.write(out, contents);
        out.name("items");
        collectionAdapter.write(out, items);
        out.name("handlerIndexes");
        collectionAdapter.write(out, value.getHandlerIndexes());
        out.endObject();
    }

    @Override
    public GUISignature read(JsonReader in) throws IOException {
        GUISignatureAdapter signature = new GUISignatureAdapter();
        Map<String, Number> contents = new HashMap<>();
        NullableArrayList<ItemStack> items = new NullableArrayList<>();
        in.beginObject();
        while (in.hasNext()) {
            switch (in.nextName()) {
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
                        signature.addHandlerIndex(index.intValue());
                    }
                    break;
                case "contents":
                    contents = mapAdapter.read(in);
                    break;
                case "items":
                    Collection<Map> itemMaps = collectionAdapter.read(in);
                    for (Map itemMap : itemMaps) {
                        items.add((ItemStack) ConfigurationSerialization.deserializeObject(itemMap));
                    }
                    break;
            }
        }

        for (Map.Entry<String, Number> entry : contents.entrySet()) {
            signature.item(
                    Integer.parseInt(entry.getKey()),
                    items.get(entry.getValue().intValue(), new ItemStack(Material.AIR))
            );
        }
        in.endObject();

        return signature;
    }

    private int add(List<ItemStack> items, ItemStack item) {
        for (int i = 0; i < items.size(); i++) {
            ItemStack elm = items.get(i);
            if (item.equals(elm))
                return i;
        }
        int ret = items.size();
        items.add(item);
        return ret;
    }
}
