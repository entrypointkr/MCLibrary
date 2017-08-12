package kr.rvs.mclibrary.struct.command;

import java.lang.reflect.Method;

/**
 * Created by Junhyeong Lim on 2017-07-27.
 */
public class CommandStorage {
    private final MCCommand command;
    private final SubCommandStorage info;
    private final Method method;

    public CommandStorage(MCCommand command, SubCommandStorage info, Method method) {
        this.command = command;
        this.info = info;
        this.method = method;
    }

    public MCCommand getCommand() {
        return command;
    }

    public SubCommandStorage getInfo() {
        return info;
    }

    public Method getMethod() {
        return method;
    }

    public CommandType getType() {
        return info.getType();
    }

    public String getUsage() {
        return info.getUsage();
    }

    public String getDescription() {
        return info.getDesc();
    }

    public String getArgs() {
        return info.getArgs();
    }

    public int getMin() {
        return info.getMin();
    }

    public int getMax() {
        return info.getMax();
    }

    public boolean hasUsage() {
        return getUsage() != null && !getUsage().isEmpty();
    }

    public boolean hasArgs() {
        return getArgs() != null && !getArgs().isEmpty();
    }

    public boolean hasDescription() {
        return getDescription() != null && !getDescription().isEmpty();
    }
}
