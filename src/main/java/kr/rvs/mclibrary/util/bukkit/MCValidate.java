package kr.rvs.mclibrary.util.bukkit;

import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

/**
 * Created by Junhyeong Lim on 2017-07-28.
 */
public class MCValidate {
    public static void specificPluginEnabled(String pluginName) {
        Plugin plugin = Bukkit.getPluginManager().getPlugin(pluginName);
        Validate.isTrue(plugin != null && plugin.isEnabled(),
                pluginName + " 이(가) 활성되어있지 않습니다.");
    }

    public static void isProtocolLibEnabled() {
        specificPluginEnabled("ProtocolLib");
    }

    public static void isBarAPIEnabled() {
        specificPluginEnabled("BarAPI");
    }
}
