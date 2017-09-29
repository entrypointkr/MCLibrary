package kr.rvs.mclibrary.bukkit.command.duplex;

import kr.rvs.mclibrary.bukkit.command.*;
import kr.rvs.mclibrary.bukkit.player.CommandSenderWrapper;

import java.util.List;

/**
 * Created by Junhyeong Lim on 2017-09-30.
 */
public class CompositionCommand implements ICommand, CommandInfo {
    private Executable executable;
    private TabCompletable completable;
    private CommandInfo commandInfo = CommandInfo.DEFAULT;

    @Override
    public void execute(CommandSenderWrapper wrapper, CommandArguments args) {
        executable.execute(wrapper, args);
    }

    @Override
    public List<String> tabComplete(CommandSenderWrapper wrapper, CommandArguments args) {
        return completable.tabComplete(wrapper, args);
    }

    @Override
    public String usage() {
        return commandInfo.usage();
    }

    @Override
    public String desc() {
        return commandInfo.desc();
    }

    @Override
    public String perm() {
        return commandInfo.perm();
    }

    public void setExecutable(Executable executable) {
        this.executable = executable;
    }

    public void setCompletable(TabCompletable completable) {
        this.completable = completable;
    }

    public void setCommandInfo(CommandInfo commandInfo) {
        this.commandInfo = commandInfo;
    }
}
