package kr.rvs.mclibrary.bukkit.command;

import kr.rvs.mclibrary.collection.VolatileArrayList;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Created by Junhyeong Lim on 2017-09-25.
 */
public class CommandArguments extends VolatileArrayList { // TODO: Implement List, Deque
    private final StringBuilder consumedArgs = new StringBuilder();

    public CommandArguments(int initialCapacity) {
        super(initialCapacity);
    }

    public CommandArguments() {
    }

    public CommandArguments(Collection<? extends String> c) {
        super(c);
    }

    public String pollFirst() {
        return remove(0, "");
    }

    public String peekFirst() {
        return get(0, "");
    }

    @Override
    public String remove(int index) {
        String old = super.remove(index);
        if (StringUtils.isNotEmpty(old)) {
            if (consumedArgs.length() > 0)
                consumedArgs.append(' ');
            consumedArgs.append(old);
        }
        return old;
    }

    public String getConsumedArgs() {
        return consumedArgs.toString();
    }
}
