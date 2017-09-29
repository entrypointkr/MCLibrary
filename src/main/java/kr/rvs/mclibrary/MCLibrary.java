package kr.rvs.mclibrary;

import kr.rvs.mclibrary.bukkit.MCUtils;
import kr.rvs.mclibrary.bukkit.command.CommandManager;
import kr.rvs.mclibrary.bukkit.inventory.gui.GUI;
import kr.rvs.mclibrary.bukkit.protocol.PacketMonitoringListener;
import kr.rvs.mclibrary.general.Version;
import kr.rvs.mclibrary.gson.GsonManager;
import kr.rvs.mclibrary.gson.SettingManager;
import kr.rvs.mclibrary.plugin.LibraryCommand;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Junhyeong Lim on 2017-07-26.
 */
public class MCLibrary extends JavaPlugin {
    public static final String PACKET_DEBUG = "packet-debug";
    public static final String DETAIL_LOG = "stacktrace";
    private static final CommandManager commandManager = new CommandManager();
    private static final GsonManager gsonManager = new GsonManager();
    private static final SettingManager settingManager = new SettingManager();
    private static final Version bukkitVersion = new Version(Bukkit.getBukkitVersion());

    private static Plugin plugin;

    public MCLibrary() {
        plugin = this;
    }

    public static CommandManager getCommandManager() {
        return commandManager;
    }

    public static GsonManager getGsonManager() {
        return gsonManager;
    }

    public static SettingManager getSettingManager() {
        return settingManager;
    }

    public static Version getBukkitVersion() {
        return bukkitVersion;
    }

    public static Plugin getPlugin() {
        return plugin;
    }

    @Override
    public void onEnable() {
        GUI.init(this);
        saveDefaultConfig();
        getCommandManager().registerAll();
        getCommandManager().registerCommand(LibraryCommand.class, this);

        getConfig().options().copyDefaults(true);
        configInit();
    }

    public void configInit() {
        if (MCUtils.isEnabled("ProtocolLib")
                && getConfig().getBoolean(PACKET_DEBUG, false)) {
            PacketMonitoringListener.register(this);
        }
    }

    @Override
    public void onDisable() {
        settingManager.save();
        saveConfig();
    }
}
