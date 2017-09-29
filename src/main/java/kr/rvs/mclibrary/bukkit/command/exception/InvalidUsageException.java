package kr.rvs.mclibrary.bukkit.command.exception;

import kr.rvs.mclibrary.bukkit.command.CommandArguments;
import kr.rvs.mclibrary.bukkit.command.Executable;
import kr.rvs.mclibrary.bukkit.command.CommandInfo;
import kr.rvs.mclibrary.bukkit.player.CommandSenderWrapper;

/**
 * Created by Junhyeong Lim on 2017-09-27.
 */
public class InvalidUsageException extends CommandException {
    private final CommandInfo commandInfo;

    public InvalidUsageException(CommandSenderWrapper wrapper, CommandArguments arguments, Executable source, CommandInfo commandInfo) {
        super(wrapper, arguments, source);
        this.commandInfo = commandInfo;
    }

    public CommandInfo getCommandInfo() {
        return commandInfo;
    }
}
