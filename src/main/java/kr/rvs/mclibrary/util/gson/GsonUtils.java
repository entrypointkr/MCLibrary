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
import java.lang.reflect.Type;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Created by Junhyeong Lim on 2017-08-21.
 */
public class GsonUtils {
    public static <T> T read(File file, Type type, Supplier<T> def) {
        Gson gson = MCLibrary.getGsonManager().getGson();
        T ret = null;

        if (file.isFile()) {
            try {
                JsonReader reader = new JsonReader(new BufferedReader(new FileReader(file)));
                ret = gson.fromJson(reader, type);
            } catch (Exception ex) {
                // Ignore
            }
        }

        return ret != null ? ret : def != null ? def.get() : null;
    }

    public static void write(File file, Object obj, Consumer<Exception> callback) {
        try (Writer writer = new BufferedWriter(new FileWriter(file))) {
            Gson gson = MCLibrary.getGsonManager().getGson();
            gson.toJson(obj, writer);
        } catch (IOException e) {
            if (callback != null)
                callback.accept(e);
        }
    }
}
