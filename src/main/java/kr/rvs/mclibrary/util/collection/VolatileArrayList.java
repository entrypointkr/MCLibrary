package kr.rvs.mclibrary.util.collection;

import java.util.Collection;

/**
 * Created by Junhyeong Lim on 2017-07-26.
 */
public class VolatileArrayList extends OptionalArrayList<String> {
    public VolatileArrayList(int initialCapacity) {
        super(initialCapacity);
    }

    public VolatileArrayList() {
    }

    public VolatileArrayList(Collection<? extends String> c) {
        super(c);
    }

    public Integer getInt(int i, int def) {
        Integer ret = def;
        String elem = get(i);

        if (elem != null) {
            try {
                ret = Integer.valueOf(elem);
            } catch (NumberFormatException ex) {
                // Ignore
            }
        }

        return ret;
    }
}
