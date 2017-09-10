package kr.rvs.mclibrary.collection;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Junhyeong Lim on 2017-08-12.
 */
public class NullableArrayList<E> extends ArrayList<E> {
    public NullableArrayList(int initialCapacity) {
        super(initialCapacity);
    }

    public NullableArrayList() {
    }

    public NullableArrayList(Collection<? extends E> c) {
        super(c);
    }

    @Override
    public E get(int index) {
        E elem = size() > index ? super.get(index) : null;
        return elem;
    }

    public E get(int index, E def) {
        E elem = get(index);
        return elem != null ? elem : def;
    }
}
