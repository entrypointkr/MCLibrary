package kr.rvs.mclibrary.struct;

import kr.rvs.mclibrary.MCLibrary;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Field;

/**
 * Created by Junhyeong Lim on 2017-07-26.
 */
public class Injector {
    public static void injectServer(Server server) {
        if (Bukkit.getServer() == null) {
            Bukkit.setServer(server);
        }
    }

    public static void injectPlugin(Plugin plugin) {
        try {
            Field field = MCLibrary.class.getDeclaredField("plugin");
            field.setAccessible(true);
            field.set(null, plugin);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
