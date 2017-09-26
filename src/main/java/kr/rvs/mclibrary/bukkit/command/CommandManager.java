package kr.rvs.mclibrary.bukkit.command;

import kr.rvs.mclibrary.MCLibrary;
import kr.rvs.mclibrary.Static;
import kr.rvs.mclibrary.bukkit.command.completor.CompositeCompleter;
import kr.rvs.mclibrary.bukkit.command.completor.ReflectiveCompletor;
import kr.rvs.mclibrary.bukkit.command.executor.AnnotationProxyExecutor;
import kr.rvs.mclibrary.bukkit.command.executor.CompositeExecutor;
import kr.rvs.mclibrary.bukkit.command.executor.ReflectiveExecutor;
import kr.rvs.mclibrary.reflection.ClassProbe;
import kr.rvs.mclibrary.reflection.FieldEx;
import kr.rvs.mclibrary.reflection.Reflections;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.SimplePluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.regex.Pattern;

/**
 * Created by Junhyeong Lim on 2017-09-25.
 */
public class CommandManager {
    public static final CommandCreator DEF_CREATOR = aClass -> {
        Constructor constructor = Reflections.getAllConstructors(aClass)[0];
        constructor.setAccessible(true);
        return constructor.newInstance();
    };
    private static final Pattern SPACE_PATTERN = Pattern.compile(" ", Pattern.LITERAL);
    private final CommandMap commandMap;

    public CommandManager(CommandMap commandMap) {
        this.commandMap = commandMap;
    }

    public CommandManager() {
        PluginManager manager = Bukkit.getPluginManager();
        try {
            Field mapField = SimplePluginManager.class.getDeclaredField("commandMap");
            mapField.setAccessible(true);
            commandMap = (CommandMap) mapField.get(manager);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
    }

    public void registerAll() {
        String name = MCLibrary.getPlugin().getName();
        for (Plugin plugin : Bukkit.getPluginManager().getPlugins()) {
            PluginDescriptionFile desc = plugin.getDescription();
            if (!desc.getSoftDepend().contains(name) && !desc.getDepend().contains(name))
                continue;

            FieldEx fileField = Reflections.getFieldEx(JavaPlugin.class, "file");
            fileField.<File>get(plugin).ifPresent(file -> {
                ClassProbe probe = new ClassProbe(file);
                for (Class<?> annotatedClass : probe.getTypesAnnotatedWith(Command.class)) {
                    registerCommand(annotatedClass, plugin);
                }
            });
        }
    }

    public void registerCommand(Class commandClass, Plugin plugin) {
        try {
            registerCommand(new CompositeExecutor(), new CompositeCompleter(), commandClass, DEF_CREATOR, true, plugin);
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
            Static.log(e);
        }
    }

    public void registerCommand(CompositeExecutor compositeExecutor, CompositeCompleter compositeCompleter, Object instance, Method method) {
        if (method.isAnnotationPresent(Command.class)) {
            Command annotation = method.getAnnotation(Command.class);
            String[] splited = SPACE_PATTERN.split(annotation.args());

            CompositeExecutor composite = compositeExecutor.setupComposite(splited, 0, splited.length - 1, new CompositeExecutor());
            ReflectiveExecutor reflectiveExecutor = new ReflectiveExecutor(instance, method);
            AnnotationProxyExecutor proxyExecutor = new AnnotationProxyExecutor(annotation, reflectiveExecutor);
            composite.put(splited[splited.length - 1], proxyExecutor);
        } else if (method.isAnnotationPresent(TabCompletor.class)) {
            TabCompletor annotation = method.getAnnotation(TabCompletor.class);
            String[] splited = SPACE_PATTERN.split(annotation.args());

            CompositeCompleter composite = compositeCompleter.setupComposite(splited, 0, splited.length - 1, new CompositeCompleter());
            composite.put(splited[splited.length - 1], new ReflectiveCompletor(instance, method));
        }
    }

    public void registerCommand(CompositeExecutor compositeExecutor, CompositeCompleter compositeCompleter,
                                Class<?> commandClass, CommandCreator creator, boolean first, Plugin plugin) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        if (!commandClass.isAnnotationPresent(Command.class)) {
            Static.log(Level.WARNING, commandClass.getSimpleName() + " is not annotation presented");
            return;
        }

        Command commandAnnot = commandClass.getAnnotation(Command.class);
        Object instance = creator.create(commandClass);
        String[] args = SPACE_PATTERN.split(commandAnnot.args());
        if (args.length == 0)
            return;

        if (first) {
            String firstArg = args[0];
            CommandAdaptor adaptor = new CommandAdaptor(
                    "mclibrary:" + firstArg,
                    "",
                    "",
                    Arrays.asList(args),
                    compositeExecutor,
                    compositeCompleter,
                    plugin
            );
            commandMap.register(firstArg, "mclibrary", adaptor);
            compositeExecutor = compositeExecutor.setupComposite(args, 1, args.length, new CompositeExecutor());
        } else {
            compositeExecutor = compositeExecutor.setupComposite(args, 0, args.length, new CompositeExecutor());
        }

        for (Method method : commandClass.getDeclaredMethods()) {
            registerCommand(compositeExecutor, compositeCompleter, instance, method);
        }

        if (commandClass.isAnnotationPresent(SubCommand.class)) {
            SubCommand subCommandAnnot = commandClass.getAnnotation(SubCommand.class);
            for (Class<?> subClass : subCommandAnnot.value()) {
                registerCommand(compositeExecutor, compositeCompleter, subClass, creator, false, plugin);
            }
        }
    }

    interface CommandCreator {
        Object create(Class aClass) throws IllegalAccessException, InvocationTargetException, InstantiationException;
    }
}
