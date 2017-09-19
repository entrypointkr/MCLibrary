package kr.rvs.mclibrary.bukkit.command.internal;

import kr.rvs.mclibrary.collection.NullableArrayList;

import java.util.Collection;

/**
 * Created by Junhyeong Lim on 2017-09-20.
 */
public class CommandArguments extends NullableArrayList<String> {
    public CommandArguments() {
    }

    public CommandArguments(int numElements) {
        super(numElements);
    }

    public CommandArguments(Collection<? extends String> c) {
        super(c);
    }

    public String pollFirst() {
        return size() > 0 ? remove(0, "") : "";
    }

    public String peekFirst() {
        return size() > 0 ? get(0, "") : "";
    }
}
