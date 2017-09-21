package kr.rvs.mclibrary.bukkit.command;

import kr.rvs.mclibrary.bukkit.command.exception.CommandException;
import kr.rvs.mclibrary.bukkit.command.exception.CommandNotFoundException;
import kr.rvs.mclibrary.bukkit.command.exception.InvalidUsageException;
import kr.rvs.mclibrary.bukkit.command.exception.PermissionDeniedException;

/**
 * Created by Junhyeong Lim on 2017-09-21.
 */
public abstract class CommandExceptionHandlerAdapter implements CommandExceptionHandler {
    @Override
    public void handle(CommandException exception) {
        if (exception instanceof CommandNotFoundException)
            handle((CommandNotFoundException) exception);
        else if (exception instanceof InvalidUsageException)
            handle((InvalidUsageException) exception);
        else if (exception instanceof PermissionDeniedException)
            handle((PermissionDeniedException) exception);
    }

    public abstract void handle(CommandNotFoundException ex);

    public abstract void handle(InvalidUsageException ex);

    public abstract void handle(PermissionDeniedException ex);
}
