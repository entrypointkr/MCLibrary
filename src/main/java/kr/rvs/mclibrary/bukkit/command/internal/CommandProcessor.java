package kr.rvs.mclibrary.bukkit.command.internal;

import kr.rvs.mclibrary.bukkit.command.BaseCommand;
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
    private final ICommand command;
    private final Plugin plugin;

    public CommandProcessor(BaseCommand base, Plugin plugin) {
        super(base.label(), base.description(), base.usage(), base.aliases());
        this.command = base.getCompiler().getCommand();
        this.plugin = plugin;
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        command.execute(sender, commandLabel, new CommandArguments(Arrays.asList(args)));
        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        List<String> ret = command.tabComplete(sender, alias, new CommandArguments(Arrays.asList(args)));
        return ret.isEmpty() ? super.tabComplete(sender, alias, args) : ret;
    }

    @Override
    public Plugin getPlugin() {
        return plugin;
    }
}
