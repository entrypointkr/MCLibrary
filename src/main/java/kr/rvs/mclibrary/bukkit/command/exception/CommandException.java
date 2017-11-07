package kr.rvs.mclibrary.bukkit.command.exception;

import java.util.Optional;

/**
 * Created by Junhyeong Lim on 2017-09-27.
 */
public class CommandException extends RuntimeException {
    private final Object source;

    public CommandException(Object source) {
        this.source = source;
    }

    public CommandException() {
        this(null);
    }

    public Optional<Object> getSource() {
        return Optional.ofNullable(source);
    }
}
