package kr.rvs.mclibrary.bukkit.event;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;

/**
 * Created by Junhyeong Lim on 2017-10-21.
 */
public abstract class CancellableDelegateEvent<E extends Event & Cancellable> extends DelegateEvent<E> implements Cancellable {
    public CancellableDelegateEvent(E delegate) {
        super(delegate);
    }

    @Override
    public boolean isCancelled() {
        return getDelegate().isCancelled();
    }

    @Override
    public void setCancelled(boolean cancel) {
        getDelegate().setCancelled(cancel);
    }
}
