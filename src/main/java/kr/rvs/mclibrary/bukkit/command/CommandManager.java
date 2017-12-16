package kr.rvs.mclibrary.bukkit.command;

import kr.rvs.mclibrary.MCLibrary;
import kr.rvs.mclibrary.Static;
import kr.rvs.mclibrary.bukkit.command.annotation.Command;
import kr.rvs.mclibrary.bukkit.command.annotation.SubCommand;
import kr.rvs.mclibrary.bukkit.command.annotation.TabCompleter;
import kr.rvs.mclibrary.bukkit.command.completor.ReflectiveCompleter;
import kr.rvs.mclibrary.bukkit.command.duplex.CompositeCommand;
import kr.rvs.mclibrary.bukkit.command.duplex.MapCommand;
import kr.rvs.mclibrary.bukkit.command.executor.AnnotationProxyExecutor;
import kr.rvs.mclibrary.bukkit.command.executor.ReflectiveExecutor;
import kr.rvs.mclibrary.bukkit.plugin.Plugins;
import kr.rvs.mclibrary.reflection.ClassProbe;
import kr.rvs.mclibrary.reflection.FieldEx;
import kr.rvs.mclibrary.reflection.Reflections;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.SimplePluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

/**
 * Created by Junhyeong Lim on 2017-09-29.
 */
public class CommandManager {
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

    public void registerAll(MCLibrary superPlugin) {
        for (Plugin plugin : Plugins.getDependPlugins(superPlugin)) {
            FieldEx fileField = Reflections.getFieldEx(JavaPlugin.class, "file");
            fileField.<File>get(plugin).ifPresent(file -> {
                ClassProbe probe = new ClassProbe(file);
                Set<Class<?>> commandClasses = probe.getTypesAnnotatedWith(Command.class);
                Set<Class<?>> subCommandClasses = probe.getTypesAnnotatedWith(SubCommand.class);

                for (Class<?> subCommandClass : subCommandClasses) {
                    SubCommand subCommand = subCommandClass.getAnnotation(SubCommand.class);
                    commandClasses.removeAll(Arrays.asList(subCommand.value()));
                }

                for (Class<?> annotatedClass : commandClasses) {
                    if (annotatedClass.isMemberClass())
                        continue;
                    registerCommand(annotatedClass, plugin);
                }
            });
        }
    }

    private Object createInstance(Class<?> commandClass) {
        try {
            Constructor constructor = Reflections.getAllConstructors(commandClass)[0];
            constructor.setAccessible(true);
            return constructor.newInstance();
        } catch (Exception ex) {
            throw new IllegalStateException(ex);
        }
    }

    public void registerCommand(Class<?> commandClass, Plugin plugin) {
        MapCommand mapCommand = new MapCommand(); // TODO: ICommand is should provide a full args
        CommandAnnotationWrapper annot = setupCommandWithClass(commandClass, mapCommand);
        for (Map.Entry<String, ICommand> entry : mapCommand.entrySet()) {
            String key = entry.getKey();
            ICommand val = entry.getValue();

            CommandAdaptor adaptor = new CommandAdaptor(
                    key,
                    annot.desc(),
                    annot.usage(),
                    Arrays.asList(annot.slicedArgs()),
                    val,
                    plugin
            );
            commandMap.register(key, plugin.getName(), adaptor);
            Static.log(String.format("&eCommand \"%s\" registered from &a%s", key, plugin.getName()));
        }
    }

    public CommandAnnotationWrapper setupCommandWithClass(Class<?> commandClass, MapCommand parent) {
        AtomicReference<CommandAnnotationWrapper> annotRef = new AtomicReference<>();
        Reflections.getAnnotation(commandClass, Command.class).ifPresent(commandAnnot -> {
            CommandAnnotationWrapper wrapper = new CommandAnnotationWrapper(commandAnnot);
            Object instance = createInstance(commandClass);
            String[] args = wrapper.slicedArgs();
            MapCommand base = parent.setupMap(args, 0, args.length);
            Set<Class<?>> subClasses = new HashSet<>(Arrays.asList(commandClass.getDeclaredClasses()));
            Reflections.getAnnotation(commandClass, SubCommand.class).ifPresent(subCommandAnnot ->
                    subClasses.addAll(Arrays.asList(subCommandAnnot.value())));

            setupCommandWithMethod(commandClass, instance, base);
            for (Class<?> subClass : subClasses) {
                setupCommandWithClass(subClass, base);
            }
            annotRef.set(wrapper);
        });
        return annotRef.get();
    }

    private void setupCommandWithMethod(Class<?> commandClass, Object instance, MapCommand parent) {
        for (Method method : commandClass.getDeclaredMethods()) {
            String[] slice = null;
            Consumer<CompositeCommand> callback = compositeCommand -> {
                // Empty
            };
            if (method.isAnnotationPresent(Command.class)) {
                CommandAnnotationWrapper annot = new CommandAnnotationWrapper(method.getAnnotation(Command.class));
                slice = annot.slicedArgs();
                callback = composition -> {
                    ReflectiveExecutor reflectiveExecutor = new ReflectiveExecutor(instance, method);
                    AnnotationProxyExecutor proxyExecutor = new AnnotationProxyExecutor(annot, reflectiveExecutor);
                    composition.setExecutable(proxyExecutor);
                    composition.setCommandInfo(proxyExecutor);
                };
            } else if (method.isAnnotationPresent(TabCompleter.class)) {
                TabCompleter annot = method.getAnnotation(TabCompleter.class);
                slice = annot.args().split(" ");
                callback = composition -> composition.setCompletable(new ReflectiveCompleter(instance, method));
            }

            // Process
            if (slice == null)
                continue;
            String lastArg = slice[slice.length - 1];
            MapCommand child = parent.setupMap(slice, 0, slice.length - 1);
            ICommand command = child.computeIfAbsent(lastArg, k -> new CompositeCommand());
            if (command instanceof MapCommand) {
                ICommand baseCommand = ((MapCommand) command).getBaseCommandWithCompute();
                command = baseCommand != null ? baseCommand : command;
            }
            if (!(command instanceof CompositeCommand)) {
                Static.log("CompositeCommand expected, but " + command.getClass().getSimpleName());
                child.put(lastArg, command = new CompositeCommand());
            }
            CompositeCommand compositeCommand = (CompositeCommand) command;
            callback.accept(compositeCommand);
            Static.log("... " + Arrays.toString(slice));
        }
    }
}
