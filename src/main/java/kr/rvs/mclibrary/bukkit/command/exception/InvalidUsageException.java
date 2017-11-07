package kr.rvs.mclibrary.bukkit.command.exception;

/**
 * Created by Junhyeong Lim on 2017-09-27.
 */
public class InvalidUsageException extends CommandException {
    private final String usage;

    public InvalidUsageException(Object source, String usage) {
        super(source);
        this.usage = usage;
    }

    public InvalidUsageException(String usage) {
        this.usage = usage;
    }

    public String getUsage() {
        return "&c" + usage;
    }
}
