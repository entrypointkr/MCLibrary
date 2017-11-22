package kr.rvs.mclibrary.bukkit.collection;

import kr.rvs.mclibrary.collection.OptionalHashMap;
import org.bukkit.entity.HumanEntity;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

/**
 * Created by Junhyeong Lim on 2017-10-06.
 */
public class PlayerHashMap<V> extends OptionalHashMap<String, V> {
    public PlayerHashMap(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    public PlayerHashMap(int initialCapacity) {
        super(initialCapacity);
    }

    public PlayerHashMap() {
    }

    public PlayerHashMap(Map<? extends String, ? extends V> m) {
        super(m);
    }

    public V put(HumanEntity entity, V value) {
        return put(entity.getName(), value);
    }

    public V get(HumanEntity entity) {
        return get(entity.getName());
    }

    public Optional<V> getOptional(HumanEntity entity) {
        return getOptional(entity.getName());
    }

    public V remove(HumanEntity entity) {
        return remove(entity.getName());
    }

    public V computeIfAbsent(HumanEntity entity, Function<String, ? extends V> mappingFunction) {
        return computeIfAbsent(entity.getName(), mappingFunction);
    }

    public boolean containsKey(HumanEntity entity) {
        return containsKey(entity.getName());
    }
}
