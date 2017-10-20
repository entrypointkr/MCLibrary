package kr.rvs.mclibrary.bukkit.collection;

import org.bukkit.entity.HumanEntity;

import java.util.Collection;
import java.util.HashSet;

/**
 * Created by Junhyeong Lim on 2017-10-08.
 */
public class PlayerHashSet<E extends HumanEntity> extends HashSet<String> {
    public PlayerHashSet() {
    }

    public PlayerHashSet(Collection<? extends String> c) {
        super(c);
    }

    public PlayerHashSet(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    public PlayerHashSet(int initialCapacity) {
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
