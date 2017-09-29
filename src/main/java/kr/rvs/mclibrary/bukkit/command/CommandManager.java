package kr.rvs.mclibrary.bukkit.command;

import kr.rvs.mclibrary.MCLibrary;
import kr.rvs.mclibrary.Static;
import kr.rvs.mclibrary.bukkit.command.completor.CompositeCompleter;
import kr.rvs.mclibrary.bukkit.command.completor.ReflectiveCompleter;
import kr.rvs.mclibrary.bukkit.command.executor.AnnotationProxyExecutor;
import kr.rvs.mclibrary.bukkit.command.executor.CompositeExecutor;
import kr.rvs.mclibrary.bukkit.command.executor.ReflectiveExecutor;
import kr.rvs.mclibrary.reflection.ClassProbe;
import kr.rvs.mclibrary.reflection.ConstructorEx;
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
import java.util.Optional;
import java.util.logging.Level;
import java.util.regex.Pattern;

/**
 * Created by Junhyeong Lim on 2017-09-29.
 */
public class CommandManager {
    public static final CommandFactory DEF_FACTORY = (commandClass, adaptor) -> {
        ConstructorEx constructorEx = Reflections.getConstructorEx(commandClass, CommandAdaptor.class);
        return constructorEx.newInstance(adaptor).orElseGet(() -> {
            Constructor constructor = commandClass.getDeclaredConstructors()[0];
            constructor.setAccessible(true);
            try {
                return constructor.newInstance();
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                throw new IllegalStateException(e);
            }
        });
    };
    public static final Pattern SPACE_PATTERN = Pattern.compile(" ", Pattern.LITERAL);
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

    private Optional<Command> getCommandAnnotation(Class<?> commandClass) {
        Optional<Command> optional = Reflections.getAnnotation(commandClass, Command.class);
        if (optional.isPresent()) {
            return optional;
        } else {
            Static.log(Level.WARNING, commandClass.getSimpleName() + " is not annotation presented");
            return Optional.empty();
        }
    }

    public void registerCommand(Class<?> commandClass, CommandFactory factory, Plugin plugin) {
        getCommandAnnotation(commandClass).ifPresent(commandAnnot -> {
            CompositeExecutor compositeExecutor = new CompositeExecutor();
            CompositeCompleter compositeCompleter = new CompositeCompleter();
            String[] args = SPACE_PATTERN.split(commandAnnot.args());
            String firstArg = args[0];
            CommandAdaptor adaptor = new CommandAdaptor(
                    firstArg,
                    commandAnnot.desc(),
                    commandAnnot.usage(),
                    Arrays.asList(args),
                    compositeExecutor,
                    compositeCompleter,
                    plugin
            );
            commandMap.register(firstArg, "mclibrary", adaptor);

            CompositeExecutor newCompositeExecutor = compositeExecutor.setupComposite(args, 1, args.length, CompositeExecutor::new);
            Object instance = factory.create(commandClass, adaptor);
            for (Method method : commandClass.getDeclaredMethods()) {
                registerCommand(method, instance, newCompositeExecutor, compositeCompleter);
            }

            Reflections.getAnnotation(commandClass, SubCommand.class).ifPresent(subCommandAnnot -> {
                for (Class<?> subClass : subCommandAnnot.value()) {
                    registerCommand(subClass, instance, newCompositeExecutor, compositeCompleter);
                }
            });
        });
    }

    public void registerCommand(Class<?> commandClass, Plugin plugin) {
        registerCommand(commandClass, DEF_FACTORY, plugin);
    }

    public void registerCommand(Method method, Object instance,
                                CompositeExecutor compositeExecutor, CompositeCompleter compositeCompleter) {
        if (method.isAnnotationPresent(Command.class)) {
            Command annotation = method.getAnnotation(Command.class);
            String[] splited = SPACE_PATTERN.split(annotation.args());

            CompositeExecutor composite = compositeExecutor.setupComposite(splited, 0, splited.length - 1, CompositeExecutor::new);
            ReflectiveExecutor reflectiveExecutor = new ReflectiveExecutor(instance, method);
            AnnotationProxyExecutor proxyExecutor = new AnnotationProxyExecutor(annotation, reflectiveExecutor);
            composite.put(splited[splited.length - 1], proxyExecutor);
        } else if (method.isAnnotationPresent(TabCompletor.class)) {
            TabCompletor annotation = method.getAnnotation(TabCompletor.class);
            String[] splited = SPACE_PATTERN.split(annotation.args());

            CompositeCompleter composite = compositeCompleter.setupComposite(splited, 0, splited.length - 1, CompositeCompleter::new);
            composite.put(splited[splited.length - 1], new ReflectiveCompleter(instance, method));
        }
    }

    public void registerCommand(Class<?> commandClass, Object instance,
                                CompositeExecutor compositeExecutor, CompositeCompleter compositeCompleter) {
        getCommandAnnotation(commandClass).ifPresent(commandAnnot -> {
            String[] args = SPACE_PATTERN.split(commandAnnot.args());
            CompositeExecutor newCompositeExecutor = compositeExecutor.setupComposite(args, 0, args.length, CompositeExecutor::new);
            for (Method method : commandClass.getDeclaredMethods()) {
                registerCommand(method, instance, newCompositeExecutor, compositeCompleter);
            }
        });
    }

    interface CommandFactory {
        Object create(Class<?> commandClass, CommandAdaptor adaptor);
    }
}
