package kr.rvs.mclibrary.bukkit.command.executor;

import kr.rvs.mclibrary.bukkit.command.CommandArguments;
import kr.rvs.mclibrary.bukkit.command.CommandExecutable;
import kr.rvs.mclibrary.bukkit.command.CompositeBase;
import kr.rvs.mclibrary.bukkit.command.exception.CommandException;
import kr.rvs.mclibrary.bukkit.command.exception.CommandNotFoundException;
import kr.rvs.mclibrary.bukkit.player.CommandSenderWrapper;

/**
 * Created by Junhyeong Lim on 2017-09-25.
 */
public class CompositeExecutor extends CompositeBase<CommandExecutable, CompositeExecutor> implements CommandExecutable {
    @Override
    public void execute(CommandSenderWrapper wrapper, CommandArguments args) throws CommandException {
        CommandExecutable command = get(args.pollFirst());
        if (command != null) {
            command.execute(wrapper, args);
        } else {
            throw new CommandNotFoundException(this);
        }
    }
}
