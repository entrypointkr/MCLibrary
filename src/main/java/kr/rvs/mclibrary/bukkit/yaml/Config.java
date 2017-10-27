package kr.rvs.mclibrary.bukkit.yaml;

import kr.rvs.mclibrary.Static;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.util.function.Consumer;

/**
 * Created by Junhyeong Lim on 2017-10-27.
 */
public class Config {
    private final FileConfiguration config;

    public Config(File file) {
        this(YamlConfiguration.loadConfiguration(ensure(file)));
    }

    public Config(FileConfiguration config) {
        this.config = config;
        config.options().copyDefaults(true);
    }

    public static File ensure(File file) {
        file.getParentFile().mkdirs();
        if (!file.isFile()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                Static.log(e);
            }
        }
        return file;
    }

    public Config(Reader reader) {
        this(YamlConfiguration.loadConfiguration(reader));
    }

    public Config(Plugin plugin) {
        this(plugin.getConfig());
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String key, T def) {
        Object data = config.get(key);
        config.addDefault(key, def);
        return def.getClass().isInstance(data) ? (T) data : def;
    }

    public void save(File file, Consumer<Exception> exceptionHandler) {
        try {
            config.save(file);
        } catch (Exception e) {
            exceptionHandler.accept(e);
        }
    }

    public void save(File file) {
        save(file, Exception::printStackTrace);
    }

    public FileConfiguration getInternalConfig() {
        return config;
    }
}
