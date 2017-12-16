package kr.rvs.mclibrary.bukkit.wizard;

import java.util.function.Consumer;

/**
 * Created by Junhyeong Lim on 2017-12-16.
 */
public abstract class DelegateWizard<C, T> extends Wizard<C> {
    private final Wizard<T> wizard;

    public DelegateWizard(Wizard<T> wizard) {
        this.wizard = wizard;
    }

    @Override
    public void release() {
        wizard.release();
    }

    protected void delegateProcess(Consumer<T> callback) {
        wizard.process(callback);
    }
}
