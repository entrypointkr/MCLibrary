package kr.rvs.mclibrary.collection;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Created by Junhyeong Lim on 2017-07-28.
 */
public class OptionalHashMap<K, V> extends HashMap<K, V> {
    public OptionalHashMap(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    public OptionalHashMap(int initialCapacity) {
        super(initialCapacity);
    }

    public OptionalHashMap() {
    }

    public OptionalHashMap(Map<? extends K, ? extends V> m) {
        super(m);
    }

    public Optional<V> getOptional(K key) {
        return key != null ? Optional.ofNullable(get(key)) : Optional.empty();
    }
}
