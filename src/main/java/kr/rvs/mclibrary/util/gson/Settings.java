package kr.rvs.mclibrary.util.gson;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import kr.rvs.mclibrary.MCLibrary;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.function.Consumer;

/**
 * Created by Junhyeong Lim on 2017-08-18.
 */
public abstract class Settings {
    public static <T extends Settings> T load(File file, Class<T> aClass, T def) {
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

        return ret != null ? ret : def;
    }

    public void save(File path, Consumer<Exception> exceptionCallback) {
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

    public void save(File path) {
        save(path, null);
    }
}
