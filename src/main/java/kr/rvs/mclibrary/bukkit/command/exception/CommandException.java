package kr.rvs.mclibrary.bukkit.command.exception;

import kr.rvs.mclibrary.bukkit.command.CommandExecutable;

/**
 * Created by Junhyeong Lim on 2017-09-27.
 */
public class CommandException extends Exception {
    private final CommandExecutable source;

    public CommandException(CommandExecutable source) {
        this.source = source;
    }

    public CommandExecutable getSource() {
        return source;
    }
}
