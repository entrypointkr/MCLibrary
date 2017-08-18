package kr.rvs.mclibrary.util.bukkit.gson;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;
import kr.rvs.mclibrary.util.bukkit.MCUtils;
import org.bukkit.inventory.meta.ItemMeta;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * Created by Junhyeong Lim on 2017-08-18.
 */
public class ItemMetaTypeAdapter implements JsonSerializer<ItemMeta>, JsonDeserializer<ItemMeta> {
    @Override
    public JsonElement serialize(ItemMeta src, Type typeOfSrc, JsonSerializationContext context) {
        return context.serialize(src.serialize());
    }

    @Override
    public ItemMeta deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        ItemMeta ret = null;
        Type type = new TypeToken<Map<String, Object>>() {
        }.getType();
        Map<String, Object> map = context.deserialize(json, type);
        Class<?> serializableCls = MCUtils.getOBCClass("inventory.CraftMetaItem$SerializableMeta");
        try {
            Method serializeMethod = serializableCls.getMethod("deserialize", Map.class);
            ret = (ItemMeta) serializeMethod.invoke(null, map);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return ret;
    }
}
