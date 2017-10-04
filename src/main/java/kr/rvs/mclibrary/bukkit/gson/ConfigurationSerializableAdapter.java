package kr.rvs.mclibrary.bukkit.gson;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.ConfigurationSerialization;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.bukkit.configuration.serialization.ConfigurationSerialization.SERIALIZED_TYPE_KEY;

/**
 * Created by Junhyeong Lim on 2017-08-28.
 */
@SuppressWarnings("unchecked")
public class ConfigurationSerializableAdapter extends TypeAdapter<ConfigurationSerializable> {
    private final TypeAdapter<Map> mapAdapter;

    public ConfigurationSerializableAdapter(TypeAdapter<Map> mapAdapter) {
        this.mapAdapter = mapAdapter;
    }

    @Override
    public void write(JsonWriter out, ConfigurationSerializable value) throws IOException {
        mapAdapter.write(out, serialize(value));
    }

    @Override
    public ConfigurationSerializable read(JsonReader in) throws IOException {
        return deserialize(mapAdapter.read(in));
    }

    public static Map<String, Object> serialize(ConfigurationSerializable serializable) {
        Map<String, Object> serialized = new HashMap<>();
        serialized.putAll(serializable.serialize());
        for (Map.Entry<String, Object> entry : serialized.entrySet()) {
            String key = entry.getKey();
            Object val = entry.getValue();

            if (val instanceof ConfigurationSerializable) {
                serialized.put(key, serialize((ConfigurationSerializable) val));
            }
        }
        serialized.put(SERIALIZED_TYPE_KEY, ConfigurationSerialization.getAlias(serializable.getClass()));
        return serialized;
    }

    public static ConfigurationSerializable deserialize(Map<String, Object> map) {
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            Object val = entry.getValue();

            if (!(val instanceof Map))
                continue;

            Map<String, Object> subMap = (Map<String, Object>) val;
            if (key.equals("enchants") && map.containsKey("meta-type")) {
                for (Map.Entry<String, Object> enchantEntry : subMap.entrySet()) {
                    String name = enchantEntry.getKey();
                    Object level = enchantEntry.getValue();
                    if (level instanceof Number && !(level instanceof Integer))
                        subMap.put(name, ((Number) level).intValue());
                }
            }
            if (subMap.containsKey(SERIALIZED_TYPE_KEY)) {
                map.put(key, deserialize(subMap));
            }
        }
        return ConfigurationSerialization.deserializeObject(map);
    }
}
