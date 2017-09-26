package kr.rvs.mclibrary.bukkit.command.exception;

import kr.rvs.mclibrary.bukkit.command.CommandExecutable;

/**
 * Created by Junhyeong Lim on 2017-09-27.
 */
public class InvalidUsageException extends CommandException {
    public InvalidUsageException(CommandExecutable source) {
        super(source);
    }
}
