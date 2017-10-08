package kr.rvs.mclibrary;

import kr.rvs.mclibrary.bukkit.MCUtils;
import kr.rvs.mclibrary.bukkit.command.CommandManager;
import kr.rvs.mclibrary.bukkit.inventory.gui.GUI;
import kr.rvs.mclibrary.bukkit.player.PlayerUtils;
import kr.rvs.mclibrary.bukkit.plugin.PluginUtils;
import kr.rvs.mclibrary.bukkit.protocol.PacketMonitoringListener;
import kr.rvs.mclibrary.general.Version;
import kr.rvs.mclibrary.gson.GsonManager;
import kr.rvs.mclibrary.gson.SettingManager;
import kr.rvs.mclibrary.plugin.LibraryCommand;
import kr.rvs.mclibrary.plugin.ServerHostnameGetter;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Junhyeong Lim on 2017-07-26.
 */
public class MCLibrary extends JavaPlugin {
    public static final String PACKET_DEBUG = "packet-debug";
    public static final String DETAIL_LOG = "stacktrace";
    private static final CommandManager COMMAND_MANAGER = new CommandManager();
    private static final GsonManager GSON_MANAGER = new GsonManager();
    private static final SettingManager SETTING_MANAGER = new SettingManager();
    private static final Version BUKKIT_VERSION = new Version(Bukkit.getBukkitVersion());
    private static String address = "unknown";

    private static Plugin plugin;

    public MCLibrary() {
        plugin = this;
    }

    public static CommandManager getCommandManager() {
        return COMMAND_MANAGER;
    }

    public static GsonManager getGsonManager() {
        return GSON_MANAGER;
    }

    public static SettingManager getSettingManager() {
        return SETTING_MANAGER;
    }

    public static Version getBukkitVersion() {
        return BUKKIT_VERSION;
    }

    public static Plugin getPlugin() {
        return plugin;
    }

    @Override
    public void onEnable() {
        // Plugin
        saveDefaultConfig();
        getConfig().options().copyDefaults(true);
        configInit();

        // Function
        GUI.init(this);
        ServerHostnameGetter.init(this);
        LibraryCommand.init(this);
        getCommandManager().registerAll(this);
        getCommandManager().registerCommand(LibraryCommand.class, this);

        // Metrics
        Metrics metrics = new Metrics(this);
        metrics.addCustomChart(new Metrics.AdvancedPie("players_by_server", () -> {
            Map<String, Integer> map = new HashMap<>();
            map.put(getAddress(), PlayerUtils.getOnlinePlayers().size());
            return map;
        }));
        metrics.addCustomChart(new Metrics.DrilldownPie("depend_plugins_by_server", () -> {
            Map<String, Map<String, Integer>> map = new HashMap<>();
            Map<String, Integer> subMap = new HashMap<>();
            for (Plugin plugin : PluginUtils.getDependPlugins(this)) {
                subMap.put(plugin.getName(), 1);
            }
            map.put(getAddress(), subMap);
            return map;
        }));
    }

    public void configInit() {
        if (MCUtils.isEnabled("ProtocolLib")
                && getConfig().getBoolean(PACKET_DEBUG, false)) {
            PacketMonitoringListener.register(this);
        }
    }

    @Override
    public void onDisable() {
        SETTING_MANAGER.save();
        saveConfig();
    }

    public static String getAddress() {
        return address;
    }

    public static void setAddress(String address) {
        MCLibrary.address = address;
    }
}
