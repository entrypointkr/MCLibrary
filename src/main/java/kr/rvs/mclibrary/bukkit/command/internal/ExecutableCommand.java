package kr.rvs.mclibrary.bukkit.command.internal;

import kr.rvs.mclibrary.bukkit.command.CommandExecutable;
import kr.rvs.mclibrary.bukkit.command.TabCompletable;
import kr.rvs.mclibrary.bukkit.player.CommandSenderWrapper;
import kr.rvs.mclibrary.collection.VolatileArrayList;
import org.bukkit.command.CommandSender;

import java.util.List;

/**
 * Created by Junhyeong Lim on 2017-09-19.
 */
public class ExecutableCommand implements ICommand {
    private final CommandExecutable executable;
    private final TabCompletable completable;

    public ExecutableCommand(CommandExecutable executable, TabCompletable completable) {
        this.executable = executable;
        this.completable = completable;
    }

    @Override
    public void execute(CommandSender sender, String label, CommandArguments args) {
        executable.execute(new CommandSenderWrapper(sender), label, new VolatileArrayList(args));
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String label, CommandArguments args) {
        return completable.tabComplete(new CommandSenderWrapper(sender), label, new VolatileArrayList(args));
    }
}
