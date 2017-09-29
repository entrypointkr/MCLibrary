package kr.rvs.mclibrary.bukkit.command.duplex;

import kr.rvs.mclibrary.Static;
import kr.rvs.mclibrary.bukkit.command.CommandArguments;
import kr.rvs.mclibrary.bukkit.command.Executable;
import kr.rvs.mclibrary.bukkit.command.ICommand;
import kr.rvs.mclibrary.bukkit.command.exception.CommandException;
import kr.rvs.mclibrary.bukkit.command.exception.CommandNotFoundException;
import kr.rvs.mclibrary.bukkit.player.CommandSenderWrapper;

import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;

/**
 * Created by Junhyeong Lim on 2017-09-25.
 */
public class ComplexCommand extends HashMap<String, ICommand> implements ICommand {
    @Override
    public void execute(CommandSenderWrapper wrapper, CommandArguments args) throws CommandException {
        Executable command = get(args.get(0, ""));
        if (command != null) {
            args.remove(0);
            command.execute(wrapper, args);
        } else {
            throw new CommandNotFoundException(wrapper, args, this);
        }
    }

    @Override
    public List<String> tabComplete(CommandSenderWrapper wrapper, CommandArguments args) {
        String arg = args.get(0, "");
        return containsKey(arg) ?
                get(args.remove(0, "")).tabComplete(wrapper, args) :
                keySet().stream()
                        .filter(key -> key.startsWith(arg))
                        .collect(Collectors.toList());
    }

    public ComplexCommand setupComposite(String[] args, int start, int end) {
        ComplexCommand ret = this;
        for (int i = start; i < end; i++) {
            String arg = args[i];
            ICommand command = ret.computeIfAbsent(arg, k -> new ComplexCommand());
            if (!(command instanceof ComplexCommand)) {
                Static.log(Level.WARNING, String.format(
                        "CompositeBase expected, but find %s, key: %s",
                        command.toString(), arg
                ));
                ret.put(arg, command = new ComplexCommand());
            }
            ret = (ComplexCommand) command;
        }
        return ret;
    }
}
