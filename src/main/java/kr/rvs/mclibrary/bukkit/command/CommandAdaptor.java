package kr.rvs.mclibrary.bukkit.command;

import kr.rvs.mclibrary.bukkit.player.CommandSenderWrapper;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginIdentifiableCommand;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Junhyeong Lim on 2017-09-25.
 */
public class CommandAdaptor extends Command implements PluginIdentifiableCommand {
    private final CommandExecutable executor;
    private final TabCompletable completer;
    private final Plugin plugin;

    public CommandAdaptor(String name, String description, String usageMessage, List<String> aliases, CommandExecutable executor, TabCompletable completor, Plugin plugin) {
        super(name, description, usageMessage, aliases);
        this.executor = executor;
        this.completer = completor;
        this.plugin = plugin;
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        CommandSenderWrapper wrapper = new CommandSenderWrapper(sender);
        CommandArguments arguments = new CommandArguments(Arrays.asList(args));
        executor.execute(wrapper, arguments);
        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        CommandSenderWrapper wrapper = new CommandSenderWrapper(sender);
        CommandArguments arguments = new CommandArguments(Arrays.asList(args));
        List<String> complete = completer.tabComplete(wrapper, arguments);
        return complete != null && !complete.isEmpty() ? complete : super.tabComplete(sender, alias, args);
    }

    @Override
    public Plugin getPlugin() {
        return plugin;
    }
}
