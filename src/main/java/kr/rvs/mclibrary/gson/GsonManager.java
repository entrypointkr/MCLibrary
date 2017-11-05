package kr.rvs.mclibrary.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import kr.rvs.mclibrary.bukkit.gson.BukkitTypeAdapterFactory;
import kr.rvs.mclibrary.bukkit.gson.MapDeserializer;

import java.util.Map;

/**
 * Created by Junhyeong Lim on 2017-08-18.
 */
public class GsonManager {
    private final GsonBuilder builder;

    public static GsonBuilder createDefaultBuilder() {
        return new GsonBuilder().setPrettyPrinting()
                .disableHtmlEscaping()
                .enableComplexMapKeySerialization()
                .registerTypeAdapterFactory(new BukkitTypeAdapterFactory())
                .registerTypeAdapter(Map.class, new MapDeserializer());
    }

    public GsonManager(GsonBuilder builder) {
        this.builder = builder;
    }

    public GsonManager() {
        this(createDefaultBuilder());
    }

    public Gson getGson() {
        return builder.create();
    }

    public GsonBuilder getBuilder() {
        return builder;
    }
}
