package kr.rvs.mclibrary.bukkit.wizard;

import kr.rvs.mclibrary.MCLibrary;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import java.util.function.Consumer;

/**
 * Created by Junhyeong Lim on 2017-12-16.
 */
public abstract class ListenerWizard<T> extends Wizard<T> {
    private Listener listener;

    public abstract Listener listener(Consumer<T> callback);

    @Override
    protected void process(Consumer<T> callback) {
        this.listener = listener(callback);
        MCLibrary.registerListener(listener);
    }

    @Override
    public void release() {
        if (listener != null)
            HandlerList.unregisterAll(listener);
    }
}
