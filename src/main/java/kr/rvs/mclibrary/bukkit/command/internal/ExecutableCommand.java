package kr.rvs.mclibrary.bukkit.command.internal;

import kr.rvs.mclibrary.bukkit.command.CommandExecutable;
import kr.rvs.mclibrary.bukkit.command.SubCommand;
import kr.rvs.mclibrary.bukkit.command.TabCompletable;
import kr.rvs.mclibrary.bukkit.player.CommandSenderWrapper;
import kr.rvs.mclibrary.collection.VolatileArrayList;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Junhyeong Lim on 2017-09-19.
 */
public class ExecutableCommand implements ICommand {
    private final CommandExecutable executable;

    public ExecutableCommand(SubCommand executable) {
        this.executable = executable;
    }

    @Override
    public void execute(CommandSender sender, String label, CommandArguments args) {
        if (executable instanceof SubCommand) {
            SubCommand command = (SubCommand) executable;
            if (command.min() > args.size() || command.max() < args.size())
                return;
        }
        executable.execute(new CommandSenderWrapper(sender), label, new VolatileArrayList(args));
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String label, CommandArguments args) {
        return executable instanceof TabCompletable ?
                ((TabCompletable) executable).tabComplete(new CommandSenderWrapper(sender), label, new VolatileArrayList(args)) :
                new ArrayList<>();
    }
}
