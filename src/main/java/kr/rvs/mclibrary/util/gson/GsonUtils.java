package kr.rvs.mclibrary.util.gson;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import kr.rvs.mclibrary.MCLibrary;
import kr.rvs.mclibrary.util.general.FileUtils;

import java.io.*;
import java.lang.reflect.Type;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Created by Junhyeong Lim on 2017-08-21.
 */
public class GsonUtils {
    public static <T> T read(File file, Type type, Supplier<T> def) {
        Optional<T> ret = read(file, type);
        return ret.orElseGet(def);
    }

    public static <T> Optional<T> read(File file, Type type) {
        Gson gson = MCLibrary.getGsonManager().getGson();
        T ret = null;

        if (file.isFile()) {
            try {
                JsonReader reader = new JsonReader(new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8")));
                ret = gson.fromJson(reader, type);
            } catch (Exception ex) {
                // Ignore
            }
        }

        return Optional.ofNullable(ret);
    }

    public static void write(File file, Object obj, Consumer<Exception> callback) {
        try {
            FileUtils.ensure(file);
            Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
            Gson gson = MCLibrary.getGsonManager().getGson();
            gson.toJson(obj, writer);
            writer.close();
        } catch (IOException e) {
            if (callback != null)
                callback.accept(e);
        }
    }
}
