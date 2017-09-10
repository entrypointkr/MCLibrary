package kr.rvs.mclibrary.bukkit.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.PluginManager;

import java.lang.reflect.Field;

/**
 * Created by Junhyeong Lim on 2017-07-26.
 */
public class CommandUtils {
    private static CommandMap map;

    static {
        PluginManager manager = Bukkit.getPluginManager();
        Class mapClass = manager.getClass();
        try {
            Field field = mapClass.getDeclaredField("commandMap");
            field.setAccessible(true);
            map = (CommandMap) field.get(manager);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void registerCommand(String label, Command command) {
        map.register(label, command);
    }
}
