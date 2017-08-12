package kr.rvs.mclibrary.struct.command;

/**
 * Created by Junhyeong Lim on 2017-08-12.
 */
public class SubCommandStorage {
    private final CommandType type;
    private final String usage;
    private final String desc;
    private final String args;
    private final int min;
    private final int max;

    public SubCommandStorage(CommandType type, String usage, String desc, String args, int min, int max) {
        this.type = type;
        this.usage = usage;
        this.desc = desc;
        this.args = args;
        this.min = min;
        this.max = max;
    }

    public SubCommandStorage(CommandArgs annot) {
        this(annot.type(), annot.usage(), annot.desc(), annot.args(), annot.min(), annot.max());
    }

    public CommandType getType() {
        return type;
    }

    public String getUsage() {
        return usage;
    }

    public String getDesc() {
        return desc;
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
}
