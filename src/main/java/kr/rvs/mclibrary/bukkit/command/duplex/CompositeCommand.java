package kr.rvs.mclibrary.bukkit.command.duplex;

import kr.rvs.mclibrary.bukkit.command.CommandArguments;
import kr.rvs.mclibrary.bukkit.command.CommandInformation;
import kr.rvs.mclibrary.bukkit.command.Executable;
import kr.rvs.mclibrary.bukkit.command.ICommand;
import kr.rvs.mclibrary.bukkit.command.TabCompletable;
import kr.rvs.mclibrary.bukkit.player.CommandSenderWrapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Junhyeong Lim on 2017-09-30.
 */
public class CompositeCommand implements ICommand, CommandInformation {
    private Executable executable;
    private TabCompletable completable;
    private CommandInformation commandInformation = CommandInformation.DEFAULT;

    @Override
    public void execute(CommandSenderWrapper wrapper, CommandArguments args) {
        if (executable != null)
            executable.execute(wrapper, args);
    }

    @Override
    public List<String> tabComplete(CommandSenderWrapper wrapper, CommandArguments args) {
        if (completable != null) {
            return completable.tabComplete(wrapper, args);
        }
        return new ArrayList<>();
    }

    @Override
    public String usage() {
        return commandInformation.usage();
    }

    @Override
    public String desc() {
        return commandInformation.desc();
    }

    @Override
    public String perm() {
        return commandInformation.perm();
    }

    public void setExecutable(Executable executable) {
        this.executable = executable;
    }

    public void setCompletable(TabCompletable completable) {
        this.completable = completable;
    }

    public void setCommandInfo(CommandInformation commandInformation) {
        this.commandInformation = commandInformation;
    }

    public Executable getExecutable() {
        return executable;
    }

    public TabCompletable getCompletable() {
        return completable;
    }

    public CommandInformation getCommandInfo() {
        return commandInformation;
    }
}
