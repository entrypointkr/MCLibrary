package kr.rvs.mclibrary.bukkit.command.exception;

/**
 * Created by Junhyeong Lim on 2017-09-27.
 */
public class CommandException extends RuntimeException {
    private final Object source;

    public CommandException(Object source) {
        this.source = source;
    }

    public Object getSource() {
        return source;
    }
}
