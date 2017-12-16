package kr.rvs.mclibrary.bukkit.plugin;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Junhyeong Lim on 2017-10-08.
 */
public class Plugins {
    public static boolean isDepend(String superPluginName, Plugin checkPlugin) {
        PluginDescriptionFile desc = checkPlugin.getDescription();
        List<String> softDepend = desc.getSoftDepend();
        List<String> depend = desc.getDepend();
        return softDepend != null && softDepend.contains(superPluginName)
                || depend != null && depend.contains(superPluginName);
    }

    public static boolean isDepend(Plugin superPlugin, Plugin checkPlugin) {
        return isDepend(superPlugin.getName(), checkPlugin);
    }

    public static Set<Plugin> getDependPlugins(Plugin superPlugin) {
        Set<Plugin> ret = new HashSet<>(); // Can't use stream for Cauldron support
        for (Plugin plugin : Bukkit.getPluginManager().getPlugins()) {
            if (isDepend(superPlugin, plugin))
                ret.add(plugin);
        }
        return ret;
    }

    private Plugins() {
    }
}
