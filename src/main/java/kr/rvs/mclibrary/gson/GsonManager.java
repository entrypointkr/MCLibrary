package kr.rvs.mclibrary.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.ObjectTypeAdapter;
import kr.rvs.mclibrary.bukkit.gson.BukkitTypeAdapterFactory;
import kr.rvs.mclibrary.bukkit.gson.NumberPredictObjectTypeAdapterFactory;
import kr.rvs.mclibrary.reflection.Reflections;

import java.lang.reflect.Field;

/**
 * Created by Junhyeong Lim on 2017-08-18.
 */
public class GsonManager {
    private final GsonBuilder builder;

    static {
        try {
            Field field = ObjectTypeAdapter.class.getField("FACTORY");
            Reflections.crackFinalFieldModifier(field);
            field.set(null, new NumberPredictObjectTypeAdapterFactory());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
    }

    public static GsonBuilder createDefaultBuilder() {
        return new GsonBuilder()
                .disableHtmlEscaping()
                .enableComplexMapKeySerialization()
                .registerTypeAdapterFactory(new BukkitTypeAdapterFactory());
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
