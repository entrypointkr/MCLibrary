package kr.rvs.mclibrary.collection;

import org.apache.commons.lang.math.NumberUtils;

import java.util.Collection;
import java.util.Optional;

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

    public Optional<Integer> getInt(int index) {
        return getOptional(index).map(NumberUtils::createInteger);
    }

    public Integer getInt(int index, Integer def) {
        return getInt(index).orElse(def);
    }

    public Optional<Double> getDouble(int index) {
        return getOptional(index).map(NumberUtils::createDouble);
    }

    public Double getDouble(int index, Double def) {
        return getDouble(index).orElse(def);
    }

    public Optional<Float> getFloat(int index) {
        return getOptional(index).map(NumberUtils::createFloat);
    }

    public Float getFloat(int index, Float def) {
        return getFloat(index).orElse(def);
    }

    public Optional<Long> getLong(int index) {
        return getOptional(index).map(NumberUtils::createLong);
    }

    public Long getLong(int index, Long def) {
        return getLong(index).orElse(def);
    }
}
