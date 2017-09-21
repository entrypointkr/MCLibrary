package kr.rvs.mclibrary.bukkit.command;

import kr.rvs.mclibrary.bukkit.command.exception.CommandException;

/**
 * Created by Junhyeong Lim on 2017-09-21.
 */
public interface CommandExceptionHandler {
    DefaultCommandExceptionHandler DEFAULT = new DefaultCommandExceptionHandler();

    void handle(CommandException exception);
}
