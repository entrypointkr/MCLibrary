package kr.rvs.mclibrary.util.bukkit.collection;

import kr.rvs.mclibrary.util.collection.OptionalHashMap;
import org.bukkit.entity.Entity;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

/**
 * Created by Junhyeong Lim on 2017-09-09.
 */
public class EntityNameHashMap<V> extends OptionalHashMap<String, V> {
    public EntityNameHashMap(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    public EntityNameHashMap(int initialCapacity) {
        super(initialCapacity);
    }

    public EntityNameHashMap() {
    }

    public EntityNameHashMap(Map<? extends String, ? extends V> m) {
        super(m);
    }

    public V put(Entity entity, V value) {
        return put(entity.getName(), value);
    }

    public V get(Entity entity) {
        return get(entity.getName());
    }

    public Optional<V> getOptional(Entity entity) {
        return getOptional(entity.getName());
    }

    public V remove(Entity entity) {
        return remove(entity.getName());
    }

    public V computeIfAbsent(Entity entity, Function<String, ? extends V> mappingFunction) {
        return computeIfAbsent(entity.getName(), mappingFunction);
    }

    public boolean containsKey(Entity entity) {
        return containsKey(entity.getName());
    }
}
