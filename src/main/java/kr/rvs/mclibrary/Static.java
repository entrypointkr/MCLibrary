package kr.rvs.mclibrary;

import kr.rvs.mclibrary.bukkit.MCUtils;
import org.bukkit.Bukkit;

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
            log(Level.WARNING, "에러가 발생했습니다. " + ex.toString());
        }
    }

    public static void log(Level level, String... messages) {
        for (String message : messages) {
            Bukkit.getLogger().log(level, "[MCLibrary] " + MCUtils.colorize(message));
        }
    }

    private static boolean getBoolean(String key) {
        return MCLibrary.getPlugin().getConfig().getBoolean(key);
    }
}
