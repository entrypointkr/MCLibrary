package kr.rvs.mclibrary.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import kr.rvs.mclibrary.bukkit.gson.BukkitTypeAdapterFactory;
import kr.rvs.mclibrary.bukkit.gson.MaterialAndDataAdapter;
import kr.rvs.mclibrary.bukkit.item.MaterialAndData;

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
                .registerTypeAdapter(MaterialAndData.class, new MaterialAndDataAdapter())
                .registerTypeAdapter(Double.class, (JsonSerializer<Double>) (src, typeOfSrc, context) ->
                        src == src.longValue()
                                ? new JsonPrimitive(src.longValue())
                                : new JsonPrimitive(src));
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
