package kr.rvs.mclibrary.util.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

/**
 * Created by Junhyeong Lim on 2017-07-28.
 */
public class OptionalArrayList<E> extends ArrayList<E> {
    public OptionalArrayList(int initialCapacity) {
        super(initialCapacity);
    }

    public OptionalArrayList() {
    }

    public OptionalArrayList(Collection<? extends E> c) {
        super(c);
    }

    public Optional<E> getOptional(int index) {
        return Optional.ofNullable(get(index));
    }
}
