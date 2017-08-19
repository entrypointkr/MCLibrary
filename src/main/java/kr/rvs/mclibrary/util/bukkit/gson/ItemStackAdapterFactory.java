package kr.rvs.mclibrary.util.bukkit.gson;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import kr.rvs.mclibrary.util.Static;
import kr.rvs.mclibrary.util.bukkit.MCUtils;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Optional;

/**
 * Created by Junhyeong Lim on 2017-08-19.
 */
@SuppressWarnings("unchecked")
public class ItemStackAdapterFactory implements TypeAdapterFactory {
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        Class<?> rawType = type.getRawType();
        if (ItemStack.class.isAssignableFrom(rawType)) {
            return (TypeAdapter<T>) new ItemStackAdapter(
                    gson.getAdapter(Map.class));
        }
        return null;
    }

    class ItemStackAdapter extends TypeAdapter<ItemStack> {
        private final TypeAdapter<Map> mapAdapter;

        public ItemStackAdapter(TypeAdapter<Map> mapAdapter) {
            this.mapAdapter = mapAdapter;
        }

        @Override
        public void write(JsonWriter out, ItemStack value) throws IOException {
            Map<String, Object> serialized = value.serialize();
            Optional.ofNullable(serialized.get("meta")).ifPresent(metaObj -> {
                serialized.put("meta", ((ItemMeta) metaObj).serialize());
            });

            mapAdapter.write(out, serialized);
        }

        @Override
        public ItemStack read(JsonReader in) throws IOException {
            Map<String, Object> itemMap = mapAdapter.read(in);
            Optional.ofNullable(itemMap.remove("meta")).ifPresent(metaMap -> {
                Class<?> serializableCls = MCUtils.getOBCClass("inventory.CraftMetaItem$SerializableMeta");
                try {
                    Method serializeMethod = serializableCls.getMethod("deserialize", Map.class);
                    Object meta = serializeMethod.invoke(null, metaMap);
                    itemMap.put("meta", meta);
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                    Static.log(e);
                }
            });
            return ItemStack.deserialize(itemMap);
        }
    }
}
