package kr.rvs.mclibrary;

import kr.rvs.mclibrary.util.bukkit.command.CommandManager;
import kr.rvs.mclibrary.util.bukkit.inventory.GUI;
import kr.rvs.mclibrary.util.gson.GsonManager;
import kr.rvs.mclibrary.util.gson.SettingsManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Junhyeong Lim on 2017-07-26.
 */
public class MCLibrary extends JavaPlugin {
    private static final CommandManager commandManager = new CommandManager();
    private static final GsonManager gsonManager = new GsonManager();
    private static final SettingsManager settingsManager = new SettingsManager();
    private static MCLibrary plugin;

    public MCLibrary() {
        plugin = this;
    }

    public static CommandManager getCommandManager() {
        return commandManager;
    }

    public static GsonManager getGsonManager() {
        return gsonManager;
    }

    public static SettingsManager getSettingsManager() {
        return settingsManager;
    }

    public static MCLibrary getPlugin() {
        return plugin;
    }

    @Override
    public void onEnable() {
        GUI.init();
    }

    @Override
    public void onDisable() {
        settingsManager.save();
    }
}
