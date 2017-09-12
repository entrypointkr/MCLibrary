package kr.rvs.mclibrary.bukkit.gson;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import kr.rvs.mclibrary.bukkit.inventory.gui.GUISignature;
import kr.rvs.mclibrary.bukkit.inventory.gui.GUISignatureAdapter;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

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
        out.name("contents");
        mapAdapter.write(out, contents);
        out.name("handlerIndexes");
        collectionAdapter.write(out, value.getHandlerIndexes());
        out.endObject();
    }

    @Override
    public GUISignature read(JsonReader in) throws IOException {
        GUISignatureAdapter signature = new GUISignatureAdapter();
        Map<String, Object> contents = new HashMap<>();
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
                        signature.addHandlerIndexes(index.intValue());
                    }
                    break;
                case "contents":
                    contents = mapAdapter.read(in);
                    break;
            }
        }

        for (Map.Entry<String, Object> entry : contents.entrySet()) {
            Integer key = Integer.parseInt(entry.getKey());
            Object value = entry.getValue();
            if (value instanceof Map) {
                signature.item(key, ConfigurationSerialization.deserializeObject((Map<String, ?>) entry.getValue()));
            } else {
                Number numVal = (Number) value;
                signature.item(key, signature.getContents().get(numVal.intValue()));
            }
        }
        in.endObject();

        return signature;
    }
}
