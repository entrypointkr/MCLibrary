package kr.rvs.mclibrary.util.bukkit.command;

import kr.rvs.mclibrary.struct.command.CommandLayout;
import kr.rvs.mclibrary.struct.command.CommandProcessor;
import kr.rvs.mclibrary.struct.command.MCCommand;
import kr.rvs.mclibrary.util.Static;
import kr.rvs.mclibrary.util.reflection.ClassProbe;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Junhyeong Lim on 2017-07-26.
 */
public class CommandManager {
    public static void setCommandHelpLayout(CommandLayout layout) {
        CommandProcessor.setCommandLayout(layout);
    }

    public void registerCommand(MCCommand command, JavaPlugin plugin) {
        CommandProcessor processor = new CommandProcessor(
                command.label(),
                command.description(),
                command.usage(),
                Arrays.asList(command.aliases()),
                command
        );

        CommandUtils.registerCommand(plugin.getName(), processor);
    }

    public void registerCommand(String packageName, JavaPlugin plugin) {
        ClassLoader pluginCL = null;

        try {
            Field field = JavaPlugin.class.getDeclaredField("classLoader");
            field.setAccessible(true);
            pluginCL = (ClassLoader) field.get(plugin);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        ClassProbe probe = new ClassProbe(packageName,
                Thread.currentThread().getContextClassLoader(), getClass().getClassLoader(), pluginCL);
        List<Class<? extends MCCommand>> classes = probe.getSubTypesOf(MCCommand.class);

        classes.forEach(aClass -> {
            try {
                registerCommand(aClass.newInstance(), plugin);
            } catch (InstantiationException | IllegalAccessException e) {
                Static.log(e);
            }
        });
    }
}
