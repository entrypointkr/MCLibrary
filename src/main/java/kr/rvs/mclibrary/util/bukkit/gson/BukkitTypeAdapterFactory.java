package kr.rvs.mclibrary.util.bukkit.gson;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

/**
 * Created by Junhyeong Lim on 2017-08-19.
 */
@SuppressWarnings("unchecked")
public class BukkitTypeAdapterFactory implements TypeAdapterFactory {
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        Class<?> rawType = type.getRawType();
        if (ItemStack.class.isAssignableFrom(rawType)) {
            return (TypeAdapter<T>) new ItemStackAdapter(gson.getAdapter(Map.class));
        } else if (Location.class.isAssignableFrom(rawType)) {
            return (TypeAdapter<T>) new LocationAdapter(gson.getAdapter(Map.class));
        }
        return null;
    }
}
