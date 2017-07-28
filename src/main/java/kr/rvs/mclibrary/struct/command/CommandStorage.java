package kr.rvs.mclibrary.struct.command;

import java.lang.reflect.Method;

/**
 * Created by Junhyeong Lim on 2017-07-27.
 */
public class CommandStorage {
    private final MCCommand command;
    private final CommandType type;
    private final String usage;
    private final String description;
    private final String args;
    private final int min;
    private final int max;
    private final Method method;

    public CommandStorage(MCCommand command, CommandType type, String usage, String description, String args, int min, int max, Method method) {
        this.command = command;
        this.type = type;
        this.usage = usage;
        this.description = description;
        this.args = args;
        this.min = min;
        this.max = max;
        this.method = method;
    }

    public CommandStorage(MCCommand command, CommandArgs annot, Method method) {
        this(command, annot.type(), annot.usage(), annot.desc(), annot.args(), annot.min(), annot.max(), method);
    }

    public CommandType getType() {
        return type;
    }

    public String getUsage() {
        return usage;
    }

    public String getDescription() {
        return description;
    }

    public String getArgs() {
        return args;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    public Method getMethod() {
        return method;
    }

    public MCCommand getCommand() {
        return command;
    }
}
