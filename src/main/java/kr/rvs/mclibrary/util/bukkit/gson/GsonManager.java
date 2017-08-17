package kr.rvs.mclibrary.util.bukkit.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;

/**
 * Created by Junhyeong Lim on 2017-08-18.
 */
public class GsonManager {
    private final GsonBuilder builder;
    private Gson cachedGson = null;

    public GsonManager(GsonBuilder builder) {
        this.builder = builder;
    }

    public GsonManager() {
        this(new GsonBuilder().setPrettyPrinting());
    }

    public Gson getGson() {
        if (cachedGson == null) {
            refresh();
        }
        return cachedGson;
    }

    public void registerTypeAdapter(Type type, Object typeAdapter) {
        builder.registerTypeAdapter(type, typeAdapter);
        refresh();
    }

    public GsonBuilder getBuilder() {
        return builder;
    }

    public void refresh() {
        cachedGson = builder.create();
    }
}
