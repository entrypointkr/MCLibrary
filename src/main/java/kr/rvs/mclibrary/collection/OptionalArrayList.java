package kr.rvs.mclibrary.collection;

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
        return Optional.ofNullable(size() > index ? get(index) : null);
    }

    public E get(int index, E def) {
        return getOptional(index).orElse(def);
    }

    public Optional<E> removeOptional(int index) {
        return Optional.ofNullable(size() > index ? remove(index) : null);
    }
}
