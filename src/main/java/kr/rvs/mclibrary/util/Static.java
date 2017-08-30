package kr.rvs.mclibrary.util;

import kr.rvs.mclibrary.ConfigKeys;
import kr.rvs.mclibrary.MCLibrary;
import kr.rvs.mclibrary.util.bukkit.MCUtils;
import org.bukkit.Bukkit;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Junhyeong Lim on 2017-07-26.
 */
public class Static {
    public static void log(Throwable ex) {
        if (MCLibrary.getPlugin().getConfig().getBoolean(ConfigKeys.STACKTRACE)) {
            Logger.getGlobal().log(Level.WARNING, "[MCLibrary] 에러가 발생했습니다.", ex);
        } else {
            log(Level.WARNING, "[MCLibrary] 에러가 발생했습니다. " + ex.toString());
        }
    }

    public static void log(Level level, String... messages) {
        for (String message : messages) {
            Bukkit.getLogger().log(level, "[MCLibrary] " + MCUtils.colorize(message));
        }
    }
}
