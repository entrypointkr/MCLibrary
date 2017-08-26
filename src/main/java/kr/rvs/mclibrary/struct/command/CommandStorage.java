package kr.rvs.mclibrary.struct.command;

import java.lang.reflect.Method;

import static org.apache.commons.lang.StringUtils.isNotEmpty;

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
        if (!method.isAccessible())
            method.setAccessible(true);
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

    public String getPermission() {
        return info.getPerm();
    }

    public int getMin() {
        return info.getMin();
    }

    public int getMax() {
        return info.getMax();
    }

    public boolean hasUsage() {
        return isNotEmpty(getUsage());
    }

    public boolean hasArgs() {
        return isNotEmpty(getArgs());
    }

    public boolean hasDescription() {
        return isNotEmpty(getDescription());
    }

    public boolean hasPermission() {
        return isNotEmpty(getPermission());
    }
}
