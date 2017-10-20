package kr.rvs.mclibrary.bukkit.event;

import org.bukkit.event.Event;

/**
 * Created by Junhyeong Lim on 2017-10-21.
 */
public abstract class DelegateEvent<E extends Event> extends Event {
    private final E delegate;

    public DelegateEvent(E delegate) {
        this.delegate = delegate;
    }

    public E getDelegate() {
        return delegate;
    }
}
