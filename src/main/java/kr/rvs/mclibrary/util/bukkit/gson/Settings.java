package kr.rvs.mclibrary.util.bukkit.gson;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import kr.rvs.mclibrary.MCLibrary;
import org.bukkit.plugin.Plugin;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

/**
 * Created by Junhyeong Lim on 2017-08-18.
 */
public abstract class Settings {
    private final Plugin plugin;

    public Settings(Plugin plugin) {
        this.plugin = plugin;
    }

    public static <T extends Settings> T load(File file, Class<T> aClass) throws FileNotFoundException {
        Gson gson = MCLibrary.getGsonManager().getGson();
        JsonReader reader = new JsonReader(new BufferedReader(new FileReader(file)));
        return gson.fromJson(reader, aClass);
    }

    public static <T extends Settings> T load(Plugin plugin, String fileName, Class<T> aClass) throws FileNotFoundException {
        return load(new File(plugin.getDataFolder(), fileName), aClass);
    }

    public void save(String name) throws IOException {
        try (Writer writer = new FileWriter(new File(plugin.getDataFolder(), name))) {
            Gson gson = MCLibrary.getGsonManager().getGson();
            gson.toJson(this, writer);
        }
    }
}
