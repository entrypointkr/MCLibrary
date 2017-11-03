package kr.rvs.mclibrary.gson;

import com.google.gson.Gson;
import kr.rvs.mclibrary.MCLibrary;
import kr.rvs.mclibrary.Static;
import kr.rvs.mclibrary.general.FileUtils;
import org.bukkit.plugin.Plugin;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Created by Junhyeong Lim on 2017-08-21.
 */
public class GsonUtils {
    public static <T> T read(Gson gson, Reader reader, Type type) {
        return gson.fromJson(reader, type);
    }

    public static <T> T read(Reader reader, Type type) {
        Gson gson = MCLibrary.getGsonManager().getGson();
        return read(gson, reader, type);
    }

    public static <T> Optional<T> read(Gson gson, File file, Type type) {
        T ret = null;

        if (file.isFile()) {
            try {
                ret = read(gson, new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8")), type);
            } catch (IOException e) {
                Static.log("Error while read " + file.getName());
                e.printStackTrace();
            }
        }

        return Optional.ofNullable(ret);
    }

    public static <T> Optional<T> read(File file, Type type) {
        return read(MCLibrary.getGsonManager().getGson(), file, type);
    }

    public static <T> T read(Gson gson, File file, Type type, Supplier<T> def) {
        Optional<T> ret = read(gson, file, type);
        return ret.orElseGet(def);
    }

    public static <T> T read(File file, Type type, Supplier<T> def) {
        return read(MCLibrary.getGsonManager().getGson(), file, type, def);
    }

    public static <T> T read(Gson gson, Plugin plugin, String fileName, Type type, Supplier<T> def) {
        return read(gson, new File(plugin.getDataFolder(), fileName), type, def);
    }

    public static <T> T read(Plugin plugin, String fileName, Type type, Supplier<T> def) {
        return read(new File(plugin.getDataFolder(), fileName), type, def);
    }

    public static void write(Gson gson, Writer writer, Object obj, Consumer<Exception> callback) {
        try {
            gson.toJson(obj, writer);
            writer.close();
        } catch (IOException e) {
            if (callback != null)
                callback.accept(e);
        }
    }

    public static void write(Writer writer, Object obj, Consumer<Exception> callback) {
        Gson gson = MCLibrary.getGsonManager().getGson();
        write(gson, writer, obj, callback);
    }

    public static void write(Gson gson, File file, Object obj, Consumer<Exception> callback) {
        FileUtils.ensure(file);
        try {
            write(gson, new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8")), obj, callback);
        } catch (UnsupportedEncodingException | FileNotFoundException e) {
            if (callback != null)
                callback.accept(e);
        }
    }

    public static void write(File file, Object obj, Consumer<Exception> callback) {
        write(MCLibrary.getGsonManager().getGson(), file, obj, callback);
    }

    public static void write(Gson gson, File file, Object obj) {
        write(gson, file, obj, ex -> Static.log("Error while write " + file.getName()));
    }

    public static void write(File file, Object obj) {
        write(MCLibrary.getGsonManager().getGson(), file, obj);
    }

    public static void write(Gson gson, Plugin plugin, String fileName, Object obj) {
        File file = new File(plugin.getDataFolder(), fileName);
        write(gson, file, obj, ex -> Static.log("Error while write " + file.getName()));
    }

    public static void write(Plugin plugin, String fileName, Object obj) {
        write(MCLibrary.getGsonManager().getGson(), new File(plugin.getDataFolder(), fileName), obj);
    }
}
