package kr.rvs.mclibrary.general;

/**
 * Created by Junhyeong Lim on 2017-10-05.
 */
public class Wrapper<T> {
    private final T handle;

    public Wrapper(T handle) {
        this.handle = handle;
    }

    public T getHandle() {
        return handle;
    }
}
