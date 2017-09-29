package kr.rvs.mclibrary.bukkit.command;

import kr.rvs.mclibrary.bukkit.command.exception.CommandNotFoundException;
import kr.rvs.mclibrary.bukkit.command.exception.InvalidUsageException;
import kr.rvs.mclibrary.bukkit.command.exception.PermissionDeniedException;

/**
 * Created by Junhyeong Lim on 2017-09-27.
 */
public interface CommandExceptionHandler {
    void handle(CommandNotFoundException ex);

    void handle(InvalidUsageException ex);

    void handle(PermissionDeniedException ex);

    void handle(Exception ex);

    default void init(CommandAdaptor adaptor) {
    }
}
