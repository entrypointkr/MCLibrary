package kr.rvs.mclibrary.bukkit.plugin;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Junhyeong Lim on 2017-10-08.
 */
public class PluginUtils {
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
        return Stream.of(Bukkit.getPluginManager().getPlugins())
                .filter(element -> isDepend(superPlugin, element))
                .collect(Collectors.toSet());
    }
}
