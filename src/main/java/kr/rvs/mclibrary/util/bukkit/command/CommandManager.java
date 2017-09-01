package kr.rvs.mclibrary.util.bukkit.command;

import kr.rvs.mclibrary.struct.command.CommandIgnore;
import kr.rvs.mclibrary.struct.command.CommandProcessor;
import kr.rvs.mclibrary.struct.command.MCCommand;
import kr.rvs.mclibrary.util.Static;
import kr.rvs.mclibrary.util.reflection.ClassProbe;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.logging.Level;

/**
 * Created by Junhyeong Lim on 2017-07-26.
 */
public class CommandManager {
    public void registerCommand(MCCommand command, JavaPlugin plugin) {
        CommandProcessor processor = new CommandProcessor(
                command.label(),
                command.description(),
                command.usage(),
                Arrays.asList(command.aliases()),
                plugin,
                command
        );

        CommandUtils.registerCommand(plugin.getName(), processor);
        Static.log(Level.INFO, "&eRegister command \"/" + command.label() + "\" from " + plugin.getName());
    }

    public void registerCommands(String packageName, JavaPlugin plugin) {
        ClassLoader pluginCL = null;

        try {
            Field field = JavaPlugin.class.getDeclaredField("classLoader");
            field.setAccessible(true);
            pluginCL = (ClassLoader) field.get(plugin);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        new ClassProbe(packageName, Thread.currentThread().getContextClassLoader(), getClass().getClassLoader(), pluginCL).getSubTypesOf(MCCommand.class)
                .stream()
                .filter(aClass -> !aClass.isInterface() && !aClass.isAnnotationPresent(CommandIgnore.class))
                .forEach(aClass -> {
                    try {
                        registerCommand(aClass.newInstance(), plugin);
                    } catch (InstantiationException | IllegalAccessException e) {
                        Static.log(e);
                    }
                });
    }

    public void registerCommands(JavaPlugin plugin) {
        String mainName = plugin.getDescription().getMain();
        registerCommands(mainName.substring(0, mainName.indexOf('.')), plugin);
    }
}
