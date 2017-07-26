package kr.rvs.mclibrary.util.collection;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Junhyeong Lim on 2017-07-26.
 */
public class VolatileArrayList extends ArrayList<String> {
    public VolatileArrayList(int initialCapacity) {
        super(initialCapacity);
    }

    public VolatileArrayList() {
    }

    public VolatileArrayList(Collection<? extends String> c) {
        super(c);
    }

    public Integer getInt(int i, int def) {
        String arg = get(i);

        try {
            return Integer.parseInt(arg);
        } catch (NumberFormatException ex) {
            return def;
        }
    }
}
