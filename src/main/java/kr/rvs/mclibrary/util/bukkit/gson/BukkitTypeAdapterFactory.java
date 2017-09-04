package kr.rvs.mclibrary.util.bukkit.gson;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.Map;

/**
 * Created by Junhyeong Lim on 2017-08-19.
 */
@SuppressWarnings("unchecked")
public class BukkitTypeAdapterFactory implements TypeAdapterFactory {
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        Class<?> rawType = type.getRawType();
        if (ConfigurationSerializable.class.isAssignableFrom(rawType)) {
            return (TypeAdapter<T>) new ConfigurationSerializableAdapter(gson.getAdapter(Map.class));
        }
        return null;
    }
}
