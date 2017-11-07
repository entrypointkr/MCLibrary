package kr.rvs.mclibrary.bukkit.command.duplex;

import kr.rvs.mclibrary.bukkit.command.CommandArguments;
import kr.rvs.mclibrary.bukkit.command.CommandInfo;
import kr.rvs.mclibrary.bukkit.command.ICommand;
import kr.rvs.mclibrary.bukkit.command.TabCompletable;
import kr.rvs.mclibrary.bukkit.command.exception.CommandException;
import kr.rvs.mclibrary.bukkit.command.exception.CommandNotFoundException;
import kr.rvs.mclibrary.bukkit.player.CommandSenderWrapper;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Junhyeong Lim on 2017-09-25.
 */
public class MapCommand extends LinkedHashMap<String, ICommand> implements ICommand {
    @Override
    public void execute(CommandSenderWrapper wrapper, CommandArguments args) throws CommandException {
        String arg = args.getOptional(0).orElse(""); // TODO: Send help message when execute failed from BaseCommand, Simplify getBaseCommand
        ICommand baseCommand = getBaseCommand();
        ICommand command = getOrDefault(arg, baseCommand);
        if (command != null) {
            if (!command.equals(baseCommand))
                args.remove(0);
            if (command instanceof CommandInfo)
                args.setLastCommand((CommandInfo) command);
            command.execute(wrapper, args);
        } else {
            throw new CommandNotFoundException(this);
        }
    }

    @Override
    public List<String> tabComplete(CommandSenderWrapper wrapper, CommandArguments args) { // /test first
        String arg = args.remove(0);
        TabCompletable completable = arg != null && args.size() > 0 ? get(arg) : getBaseTabCompleter();
        return completable != null ?
                completable.tabComplete(wrapper, args) :
                keySet().stream()
                        .filter(key -> key.startsWith(arg != null ? arg : ""))
                        .collect(Collectors.toList());
    }

    public ICommand getBaseCommand() {
        return get("");
    }

    public ICommand getBaseCommandWithCompute() {
        return computeIfAbsent("", k -> new CompositeCommand());
    }

    public TabCompletable getBaseTabCompleter() {
        ICommand command = getBaseCommand();
        return command instanceof CompositeCommand ? ((CompositeCommand) command).getCompletable() : command;
    }

    @SuppressWarnings("unchecked")
    public <T extends ICommand> T get(String key, Class<? extends T> commandClass) {
        ICommand element = get(key);
        if (element != null && commandClass.isInstance(element)) {
            return (T) get(key);
        }
        return null;
    }

    public MapCommand setupMap(String[] args, int start, int end) {
        MapCommand ret = this;
        for (int i = start; i < end; i++) {
            String arg = args[i];
            ICommand command = ret.computeIfAbsent(arg, k -> new MapCommand());
            if (!(command instanceof MapCommand)) {
                MapCommand mapCommand = new MapCommand();
                mapCommand.put("", command);
                ret.put(arg, command = mapCommand);
            }
            ret = (MapCommand) command;
        }
        return ret;
    }
}
