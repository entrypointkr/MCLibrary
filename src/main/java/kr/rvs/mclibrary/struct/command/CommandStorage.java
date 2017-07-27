package kr.rvs.mclibrary.struct.command;

import java.lang.reflect.Method;

/**
 * Created by Junhyeong Lim on 2017-07-27.
 */
public class CommandStorage {
    private final MCCommand command;
    private final CommandType type;
    private final String usage;
    private final String args;
    private final int min;
    private final int max;
    private final Method method;

    public CommandStorage(MCCommand command, CommandType type, String usage, String args, int min, int max, Method method) {
        this.command = command;
        this.type = type;
        this.usage = usage;
        this.args = args;
        this.min = min;
        this.max = max;
        this.method = method;
    }

    public CommandStorage(MCCommand command, CommandArgs annot, Method method) {
        this.command = command;
        this.type = annot.type();
        this.usage = annot.usage();
        this.args = annot.args();
        this.min = annot.min();
        this.max = annot.max();
        this.method = method;
    }

    public CommandType getType() {
        return type;
    }

    public String getUsage() {
        return usage;
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
