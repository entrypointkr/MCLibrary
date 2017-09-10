package kr.rvs.mclibrary.general;

/**
 * Created by Junhyeong Lim on 2017-08-18.
 */
public abstract class Wrapper<T> {
    private final T handle;

    public Wrapper(T handle) {
        this.handle = handle;
    }

    public T getHandle() {
        return handle;
    }
}
