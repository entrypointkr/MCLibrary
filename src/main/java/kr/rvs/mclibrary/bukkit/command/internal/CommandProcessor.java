package kr.rvs.mclibrary.bukkit.command.internal;

import kr.rvs.mclibrary.bukkit.command.BaseCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginIdentifiableCommand;
import org.bukkit.plugin.Plugin;

import java.util.ArrayDeque;
import java.util.Arrays;

/**
 * Created by Junhyeong Lim on 2017-09-18.
 */
public class CommandProcessor extends Command implements PluginIdentifiableCommand {
    private final ICommand command;
    private final Plugin plugin;

    public CommandProcessor(BaseCommand base, ICommand command, Plugin plugin) {
        super(base.label(), base.description(), base.usage(), base.aliases());
        this.command = command;
        this.plugin = plugin;
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        command.execute(sender, commandLabel, new ArrayDeque<>(Arrays.asList(args)));
        return true;
    }

    @Override
    public Plugin getPlugin() {
        return plugin;
    }
}
