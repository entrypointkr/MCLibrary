package kr.rvs.mclibrary.bukkit.command;

import kr.rvs.mclibrary.bukkit.command.internal.CommandProcessor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import java.lang.reflect.Field;

/**
 * Created by Junhyeong Lim on 2017-09-19.
 */
public class CommandManager {
    private final CommandMap commandMap;

    public CommandManager() {
        try {
            PluginManager manager = Bukkit.getPluginManager();
            Field field = manager.getClass().getDeclaredField("commandMap");
            field.setAccessible(true);
            commandMap = (CommandMap) field.get(manager);
        } catch (Exception ex) {
            throw new IllegalStateException(ex);
        }
    }

    public void registerCommands(Plugin plugin, BaseCommand... commands) {
        for (BaseCommand command : commands) {
            CommandProcessor processor = new CommandProcessor(command, plugin);
            commandMap.register(command.label(), plugin.getName(), processor);
        }
    }
}
