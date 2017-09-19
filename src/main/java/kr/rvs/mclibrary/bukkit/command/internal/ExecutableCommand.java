package kr.rvs.mclibrary.bukkit.command.internal;

import kr.rvs.mclibrary.bukkit.command.SubCommand;
import kr.rvs.mclibrary.bukkit.player.CommandSenderWrapper;
import kr.rvs.mclibrary.collection.VolatileArrayList;
import org.bukkit.command.CommandSender;

import java.util.Queue;

/**
 * Created by Junhyeong Lim on 2017-09-19.
 */
public class ExecutableCommand implements ICommand {
    private final SubCommand executable;

    public ExecutableCommand(SubCommand executable) {
        this.executable = executable;
    }

    @Override
    public void execute(CommandSender sender, String label, Queue<String> args) {
        executable.execute(new CommandSenderWrapper(sender), label, new VolatileArrayList(args));
    }
}
