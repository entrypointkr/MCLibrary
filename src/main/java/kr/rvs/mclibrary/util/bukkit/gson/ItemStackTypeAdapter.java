package kr.rvs.mclibrary.util.bukkit.gson;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * Created by Junhyeong Lim on 2017-08-18.
 */
public class ItemStackTypeAdapter implements JsonSerializer<ItemStack>, JsonDeserializer<ItemStack> {
    @Override
    public JsonElement serialize(ItemStack src, Type typeOfSrc, JsonSerializationContext context) {
        Map<String, Object> serialized = src.serialize();
        Object meta = serialized.remove("meta");
        JsonElement metaElement = context.serialize(meta, ItemMeta.class);
        serialized.put("meta", metaElement);

        return context.serialize(serialized);
    }

    @Override
    public ItemStack deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject obj = json.getAsJsonObject();
        JsonElement metaElm = obj.remove("meta");
        ItemMeta meta = context.deserialize(metaElm, ItemMeta.class);
        Map<String, Object> map = context.deserialize(obj, new TypeToken<Map<String, Object>>() {
        }.getType());
        map.put("meta", meta);

        return ItemStack.deserialize(map);
    }
}
