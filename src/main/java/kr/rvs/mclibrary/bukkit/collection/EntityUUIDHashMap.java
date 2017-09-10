package kr.rvs.mclibrary.bukkit.collection;

import kr.rvs.mclibrary.collection.OptionalHashMap;
import org.bukkit.entity.Entity;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

/**
 * Created by Junhyeong Lim on 2017-07-28.
 */
public class EntityUUIDHashMap<V> extends OptionalHashMap<UUID, V> {
    public EntityUUIDHashMap(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    public EntityUUIDHashMap(int initialCapacity) {
        super(initialCapacity);
    }

    public EntityUUIDHashMap() {
    }

    public EntityUUIDHashMap(Map<? extends UUID, ? extends V> m) {
        super(m);
    }

    public V put(Entity entity, V value) {
        return put(entity.getUniqueId(), value);
    }

    public V get(Entity entity) {
        return get(entity.getUniqueId());
    }

    public Optional<V> getOptional(Entity entity) {
        return getOptional(entity.getUniqueId());
    }

    public V remove(Entity entity) {
        return remove(entity.getUniqueId());
    }

    public V computeIfAbsent(Entity entity, Function<UUID, ? extends V> mappingFunction) {
        return computeIfAbsent(entity.getUniqueId(), mappingFunction);
    }

    public boolean containsKey(Entity entity) {
        return containsKey(entity.getUniqueId());
    }
}
