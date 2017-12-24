package kr.rvs.mclibrary.bukkit.wizard;

import java.util.function.Consumer;

/**
 * Created by Junhyeong Lim on 2017-12-16.
 */
public abstract class DelegateWizard<D, T> extends Wizard<T> {
    private final Wizard<D> wizard;

    public DelegateWizard(Wizard<D> wizard) {
        this.wizard = wizard;
    }

    @Override
    public void release() {
        wizard.release();
    }

    @Override
    protected void process(Consumer<T> callback) {
        wizard.process(processor(callback));
    }

    protected abstract Consumer<D> processor(Consumer<T> callback);
}
