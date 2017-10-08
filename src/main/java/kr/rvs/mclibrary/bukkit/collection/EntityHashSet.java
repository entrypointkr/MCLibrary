package kr.rvs.mclibrary.bukkit.collection;

import org.bukkit.entity.Entity;

import java.util.Collection;
import java.util.HashSet;

/**
 * Created by Junhyeong Lim on 2017-10-08.
 */
public class EntityHashSet<E extends Entity> extends HashSet<String> {
    public EntityHashSet() {
    }

    public EntityHashSet(Collection<? extends String> c) {
        super(c);
    }

    public EntityHashSet(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    public EntityHashSet(int initialCapacity) {
        super(initialCapacity);
    }

    public boolean add(E entity) {
        return add(entity.getName());
    }

    public boolean remove(E entity) {
        return remove(entity.getName());
    }

    public boolean contains(E entity) {
        return contains(entity.getName());
    }
}
