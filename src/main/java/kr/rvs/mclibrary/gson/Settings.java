package kr.rvs.mclibrary.gson;

import kr.rvs.mclibrary.MCLibrary;
import kr.rvs.mclibrary.Static;
import org.bukkit.plugin.Plugin;

import java.io.File;
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
        T ret = GsonUtils.read(file, aClass, def);
        if (ret != null)
            ret.setPath(file);

        return ret;
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

            if (ret != null) {
                GsonUtils.write(file, ret, Exception::printStackTrace);
            }

            return ret;
        });
    }

    public static <T extends Settings> T load(Plugin plugin, String fileName, Class<T> aClass, Supplier<T> def) {
        return load(new File(plugin.getDataFolder(), fileName), aClass, def);
    }

    public static <T extends Settings> T load(Plugin plugin, String fileName, Class<T> aClass) {
        return load(new File(plugin.getDataFolder(), fileName), aClass);
    }

    public void setPath(File path) {
        this.path = path;
    }

    public void save(Consumer<Exception> exceptionCallback) {
        GsonUtils.write(path, this, exceptionCallback);
    }

    public void save() {
        save(Exception::printStackTrace);
    }

    @SuppressWarnings("unchecked")
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
