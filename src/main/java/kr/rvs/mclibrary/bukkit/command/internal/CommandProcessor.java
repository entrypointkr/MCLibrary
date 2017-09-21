package kr.rvs.mclibrary.bukkit.command.internal;

import kr.rvs.mclibrary.bukkit.command.BaseCommand;
import kr.rvs.mclibrary.bukkit.command.CommandExceptionHandler;
import kr.rvs.mclibrary.bukkit.command.exception.CommandException;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginIdentifiableCommand;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Junhyeong Lim on 2017-09-18.
 */
public class CommandProcessor extends Command implements PluginIdentifiableCommand {
    private final BaseCommand base;
    private final ICommand command;
    private final Plugin plugin;
    private final CommandExceptionHandler exceptionHandler;

    public CommandProcessor(BaseCommand base, ICommand command, Plugin plugin) {
        super(base.getLabel(), base.description(), base.usage(), base.aliases());
        this.base = base;
        this.command = command;
        this.plugin = plugin;
        this.exceptionHandler = base.exceptionHandler();
    }

    public CommandProcessor(BaseCommand base, Plugin plugin) {
        this(base, new CommandCompiler(base).compile(), plugin);
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        try {
            command.execute(sender, base, commandLabel, new CommandArguments(Arrays.asList(args)));
        } catch (CommandException ex) {
            exceptionHandler.handle(ex);
        }
        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        List<String> ret = command.tabComplete(sender, alias, new CommandArguments(Arrays.asList(args)));
        return ret != null && ret.isEmpty() ? super.tabComplete(sender, alias, args) : ret;
    }

    @Override
    public Plugin getPlugin() {
        return plugin;
    }
}
