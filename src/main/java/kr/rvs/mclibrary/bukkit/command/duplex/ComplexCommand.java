package kr.rvs.mclibrary.bukkit.command.duplex;

import kr.rvs.mclibrary.bukkit.command.CommandArguments;
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
public class ComplexCommand extends LinkedHashMap<String, ICommand> implements ICommand {
    private ICommand absoluteCommand;

    @Override
    public void execute(CommandSenderWrapper wrapper, CommandArguments args) throws CommandException {
        String arg = args.get(0);
        ICommand command = arg != null ? get(arg) : absoluteCommand;
        if (command != null) {
            args.remove(0);
            command.execute(wrapper, args);
        } else {
            throw new CommandNotFoundException(wrapper, args, this);
        }
    }

    @Override
    public List<String> tabComplete(CommandSenderWrapper wrapper, CommandArguments args) { // /test first
        String arg = args.remove(0);
        TabCompletable completable = arg != null && args.size() > 0 ? get(arg) : absoluteCommand;
        return completable != null ?
                completable.tabComplete(wrapper, args) :
                keySet().stream()
                        .filter(key -> key.startsWith(arg != null ? arg : ""))
                        .collect(Collectors.toList());
    }

    public ComplexCommand setupComposite(String[] args, int start, int end) {
        ComplexCommand ret = this;
        for (int i = start; i < end; i++) {
            String arg = args[i];
            ICommand command = ret.computeIfAbsent(arg, k -> new ComplexCommand());
            if (!(command instanceof ComplexCommand)) {
                ComplexCommand complexCommand = new ComplexCommand();
                complexCommand.setAbsoluteCommand(command);
                ret.put(arg, command = complexCommand);
            }
            ret = (ComplexCommand) command;
        }
        return ret;
    }

    public ICommand getAbsoluteCommand() {
        return absoluteCommand;
    }

    public void setAbsoluteCommand(ICommand absoluteCommand) {
        this.absoluteCommand = absoluteCommand;
    }
}
