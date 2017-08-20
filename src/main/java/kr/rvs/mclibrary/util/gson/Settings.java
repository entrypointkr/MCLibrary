package kr.rvs.mclibrary.util.gson;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import kr.rvs.mclibrary.MCLibrary;
import kr.rvs.mclibrary.util.Static;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Constructor;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Created by Junhyeong Lim on 2017-08-18.
 */
public abstract class Settings {
    private transient File path;

    public Settings(File path) {
        this.path = path;
    }

    public static <T extends Settings> T load(File file, Class<T> aClass, Supplier<T> def) {
        T ret = null;
        try {
            if (file.isFile()) {
                Gson gson = MCLibrary.getGsonManager().getGson();
                JsonReader reader = new JsonReader(new BufferedReader(new FileReader(file)));
                ret = gson.fromJson(reader, aClass);
            } else {
                file.getParentFile().mkdirs();
            }
        } catch (Exception ex) {
            // Ignore
        }

        return ret != null ? ret : def.get();
    }

    public static <T extends Settings> T load(File file, Class<T> aClass) {
        return load(file, aClass, () -> {
            T ret = null;
            try {
                Constructor<T> aConstructor = aClass.getConstructor(File.class);
                ret = aConstructor.newInstance(file);
            } catch (Exception e) {
                Static.log(e);
            }

            return ret;
        });
    }

    public void save(Consumer<Exception> exceptionCallback) {
        try {
            try (Writer writer = new BufferedWriter(new FileWriter(path))) {
                Gson gson = MCLibrary.getGsonManager().getGson();
                gson.toJson(this, writer);
            }
        } catch (IOException e) {
            if (exceptionCallback != null)
                exceptionCallback.accept(e);
        }
    }

    public void save() {
        save(Exception::printStackTrace);
    }

    public <T extends Settings> T setAutoSave() {
        MCLibrary.getSettingManager().add(this);
        return (T) this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Settings settings = (Settings) o;

        return path != null ? path.equals(settings.path) : settings.path == null;
    }

    @Override
    public int hashCode() {
        return path != null ? path.hashCode() : 0;
    }
}
