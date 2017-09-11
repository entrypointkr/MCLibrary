package kr.rvs.mclibrary.bukkit.gson;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import kr.rvs.mclibrary.bukkit.inventory.gui.GUISignature;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.Collection;
import java.util.Map;

/**
 * Created by Junhyeong Lim on 2017-08-19.
 */
@SuppressWarnings("unchecked")
public class BukkitTypeAdapterFactory implements TypeAdapterFactory {
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        Class<?> rawType = type.getRawType();
        TypeAdapter<Map> mapAdapter = gson.getAdapter(Map.class);
        TypeAdapter<Collection> collectionAdapter = gson.getAdapter(Collection.class);
        if (ConfigurationSerializable.class.isAssignableFrom(rawType)) {
            return (TypeAdapter<T>) new ConfigurationSerializableAdapter(mapAdapter);
        } else if (GUISignature.class.isAssignableFrom(rawType)) {
            return (TypeAdapter<T>) new GUISignatureTypeAdapter(mapAdapter, collectionAdapter);
        }
        return null;
    }
}
