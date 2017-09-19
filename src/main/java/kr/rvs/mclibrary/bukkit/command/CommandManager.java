package kr.rvs.mclibrary.bukkit.command;

import kr.rvs.mclibrary.bukkit.command.internal.CommandProcessor;
import kr.rvs.mclibrary.bukkit.player.CommandSenderWrapper;
import kr.rvs.mclibrary.collection.VolatileArrayList;
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

    public void registerCommand(Plugin plugin, BaseCommand... commands) {
        for (BaseCommand command : commands) {
            CommandProcessor processor = new CommandProcessor(
                    command,
                    null,
                    plugin
            );
            commandMap.register(command.label(), plugin.getName(), processor);
        }
    }

    static class TestCommand extends BaseCommand {
        @Override
        public String label() {
            return "test";
        }
    }

    static class TestSubCommand implements SubCommand {
        @Override
        public String args() {
            return "a b";
        }

        @Override
        public void execute(CommandSenderWrapper sender, String label, VolatileArrayList args) {
            System.out.println(args.toString());
        }
    }
}
