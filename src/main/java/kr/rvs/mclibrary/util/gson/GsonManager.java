package kr.rvs.mclibrary.util.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import kr.rvs.mclibrary.util.bukkit.gson.ItemMetaTypeAdapter;
import kr.rvs.mclibrary.util.bukkit.gson.ItemStackAdapterFactory;
import org.bukkit.inventory.meta.ItemMeta;

import java.lang.reflect.Type;

/**
 * Created by Junhyeong Lim on 2017-08-18.
 */
public class GsonManager {
    private final GsonBuilder builder;

    public GsonManager(GsonBuilder builder) {
        this.builder = builder;
    }

    public GsonManager() {
        this(new GsonBuilder().setPrettyPrinting()
                .registerTypeAdapter(ItemMeta.class, new ItemMetaTypeAdapter())
                .registerTypeAdapterFactory(new ItemStackAdapterFactory()));
        // TODO
    }

    public Gson getGson() {
        return builder.create();
    }

    public void registerTypeAdapter(Type type, Object typeAdapter) {
        builder.registerTypeAdapter(type, typeAdapter);
    }

    public GsonBuilder getBuilder() {
        return builder;
    }
}
