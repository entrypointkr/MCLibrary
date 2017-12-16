package kr.rvs.mclibrary.bukkit.wizard;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Created by Junhyeong Lim on 2017-12-16.
 */
public abstract class Wizard<C> {
    protected abstract void process(Consumer<C> callback);

    protected abstract void release();

    public void start(BiConsumer<Wizard<C>, C> callback) {
        process(data -> callback.accept(this, data));
    }

    public void startOnce(BiConsumer<Wizard<C>, C> callback) {
        process(data -> {
            callback.accept(this, data);
            release();
        });
    }
}
