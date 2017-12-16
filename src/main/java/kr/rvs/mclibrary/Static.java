package kr.rvs.mclibrary;

import kr.rvs.mclibrary.bukkit.MCUtils;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

import static kr.rvs.mclibrary.MCLibrary.DETAIL_LOG;

/**
 * Created by Junhyeong Lim on 2017-07-26.
 */
public class Static {
    public static void log(Throwable ex) {
        if (getBoolean(DETAIL_LOG)) {
            Logger.getGlobal().log(Level.WARNING, "[MCLibrary] 에러가 발생했습니다.", ex);
        } else {
            log("에러가 발생했습니다. " + ex.toString());
        }
    }

    public static void log(String... messages) {
        for (String message : messages) {
            Bukkit.getConsoleSender().sendMessage("[MCLibrary] " + MCUtils.colorize(message));
        }
    }

    private static boolean getBoolean(String key) {
        return MCLibrary.getPlugin().getConfig().getBoolean(key);
    }

    public static void getAddressAsync(Plugin plugin, Consumer<String> callback) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        new URL("http://checkip.amazonaws.com").openStream()));
                callback.accept(reader.readLine());
            } catch (IOException e) {
                Static.log(e);
            }
        });
    }

    private Static() {
    }
}
