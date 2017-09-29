package kr.rvs.mclibrary.bukkit.command;

import kr.rvs.mclibrary.MCLibrary;
import kr.rvs.mclibrary.Static;
import kr.rvs.mclibrary.bukkit.command.annotation.Command;
import kr.rvs.mclibrary.bukkit.command.annotation.SubCommand;
import kr.rvs.mclibrary.bukkit.command.annotation.TabCompleter;
import kr.rvs.mclibrary.bukkit.command.completor.ReflectiveCompleter;
import kr.rvs.mclibrary.bukkit.command.duplex.CompositionCommand;
import kr.rvs.mclibrary.bukkit.command.executor.AnnotationProxyExecutor;
import kr.rvs.mclibrary.bukkit.command.duplex.ComplexCommand;
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
            ComplexCommand complexCommand = new ComplexCommand();
            String[] args = SPACE_PATTERN.split(commandAnnot.args());
            String firstArg = args[0];
            CommandAdaptor adaptor = new CommandAdaptor(
                    firstArg,
                    commandAnnot.desc(),
                    commandAnnot.usage(),
                    Arrays.asList(args),
                    complexCommand,
                    plugin
            );
            commandMap.register(firstArg, "mclibrary", adaptor);

            ComplexCommand newComplexCommand = complexCommand.setupComposite(args, 1, args.length);
            Object instance = factory.create(commandClass, adaptor);
            for (Method method : commandClass.getDeclaredMethods()) {
                registerCommand(method, instance, newComplexCommand);
            }

            Reflections.getAnnotation(commandClass, SubCommand.class).ifPresent(subCommandAnnot -> {
                for (Class<?> subClass : subCommandAnnot.value()) {
                    registerCommand(subClass, instance, newComplexCommand);
                }
            });
        });
    }

    public void registerCommand(Class<?> commandClass, Plugin plugin) {
        registerCommand(commandClass, DEF_FACTORY, plugin);
    }

    public void registerCommand(Method method, Object instance,
                                ComplexCommand complexCommand) {
        Command commandAnnot = method.getAnnotation(Command.class);
        TabCompleter completerAnnot = method.getAnnotation(TabCompleter.class);
        if (commandAnnot == null && completerAnnot == null)
            return;

        String args = commandAnnot != null ?
                commandAnnot.args() :
                completerAnnot.args();
        String[] splited = SPACE_PATTERN.split(args);
        String lastArg = splited[splited.length - 1];
        ComplexCommand composite = complexCommand.setupComposite(splited, 0, splited.length - 1);
        ICommand command = composite.computeIfAbsent(lastArg, k -> new CompositionCommand());

        if (!(command instanceof CompositionCommand)) {
            Static.log(Level.WARNING, "CompositionCommand expected, but " + command.getClass().getSimpleName());
            composite.put(lastArg, command = new CompositionCommand());
        }

        CompositionCommand compositionCommand = (CompositionCommand) command;

        if (commandAnnot != null) {
            ReflectiveExecutor reflectiveExecutor = new ReflectiveExecutor(instance, method);
            AnnotationProxyExecutor proxyExecutor = new AnnotationProxyExecutor(commandAnnot, reflectiveExecutor);
            compositionCommand.setExecutable(proxyExecutor);
            compositionCommand.setCommandInfo(proxyExecutor);
        }
        if (completerAnnot != null) {
            compositionCommand.setCompletable(new ReflectiveCompleter(instance, method));
        }
    }

    public void registerCommand(Class<?> commandClass, Object instance,
                                ComplexCommand complexCommand) {
        getCommandAnnotation(commandClass).ifPresent(commandAnnot -> {
            String[] args = SPACE_PATTERN.split(commandAnnot.args());
            ComplexCommand newComplexCommand = complexCommand.setupComposite(args, 0, args.length);
            for (Method method : commandClass.getDeclaredMethods()) {
                registerCommand(method, instance, newComplexCommand);
            }
        });
    }

    interface CommandFactory {
        Object create(Class<?> commandClass, CommandAdaptor adaptor);
    }
}
