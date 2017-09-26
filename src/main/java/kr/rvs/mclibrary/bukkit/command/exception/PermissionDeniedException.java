package kr.rvs.mclibrary.bukkit.command.exception;

import kr.rvs.mclibrary.bukkit.command.CommandExecutable;

/**
 * Created by Junhyeong Lim on 2017-09-27.
 */
public class PermissionDeniedException extends CommandException {
    public PermissionDeniedException(CommandExecutable source) {
        super(source);
    }
}
