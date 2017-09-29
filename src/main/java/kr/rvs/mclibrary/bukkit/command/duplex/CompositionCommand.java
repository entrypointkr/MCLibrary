package kr.rvs.mclibrary.bukkit.command.duplex;

import kr.rvs.mclibrary.bukkit.command.CommandArguments;
import kr.rvs.mclibrary.bukkit.command.Executable;
import kr.rvs.mclibrary.bukkit.command.ICommand;
import kr.rvs.mclibrary.bukkit.command.TabCompletable;
import kr.rvs.mclibrary.bukkit.player.CommandSenderWrapper;

import java.util.List;

/**
 * Created by Junhyeong Lim on 2017-09-30.
 */
public class CompositionCommand implements ICommand {
    private Executable executable;
    private TabCompletable completable;

    @Override
    public void execute(CommandSenderWrapper wrapper, CommandArguments args) {
        executable.execute(wrapper, args);
    }

    @Override
    public List<String> tabComplete(CommandSenderWrapper wrapper, CommandArguments args) {
        return completable.tabComplete(wrapper, args);
    }

    public void setExecutable(Executable executable) {
        this.executable = executable;
    }

    public void setCompletable(TabCompletable completable) {
        this.completable = completable;
    }
}
