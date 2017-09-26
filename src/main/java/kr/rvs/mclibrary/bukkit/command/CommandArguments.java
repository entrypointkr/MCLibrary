package kr.rvs.mclibrary.bukkit.command;

import kr.rvs.mclibrary.collection.VolatileArrayList;

import java.util.Collection;

/**
 * Created by Junhyeong Lim on 2017-09-25.
 */
public class CommandArguments extends VolatileArrayList {
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
}
