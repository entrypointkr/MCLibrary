package kr.rvs.mclibrary.bukkit.command.exception;

import kr.rvs.mclibrary.bukkit.command.CommandArguments;
import kr.rvs.mclibrary.bukkit.command.CommandInfo;
import kr.rvs.mclibrary.bukkit.command.Executable;
import kr.rvs.mclibrary.bukkit.player.CommandSenderWrapper;

/**
 * Created by Junhyeong Lim on 2017-09-27.
 */
public class InvalidUsageException extends CommandException {
    private final CommandInfo commandInfo;
    private final String message;

    public InvalidUsageException(CommandSenderWrapper wrapper, CommandArguments arguments, Executable source, CommandInfo commandInfo, String message) {
        super(wrapper, arguments, source);
        this.commandInfo = commandInfo;
        this.message = message;
    }

    public CommandInfo getCommandInfo() {
        return commandInfo;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
