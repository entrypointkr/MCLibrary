package kr.rvs.mclibrary.bukkit.wizard;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Created by Junhyeong Lim on 2017-12-16.
 */
public abstract class Wizard<T> {
    protected abstract void process(Consumer<T> callback);

    public abstract void release();

    public void start(BiConsumer<Wizard<T>, T> callback) {
        process(data -> callback.accept(this, data));
    }

    public void startOnce(BiConsumer<Wizard<T>, T> callback) {
        process(data -> {
            callback.accept(this, data);
            release();
        });
    }
}
