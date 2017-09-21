package kr.rvs.mclibrary.general;

import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by Junhyeong Lim on 2017-09-20.
 */
public class MethodWrapper extends AtomicReference<Object> {
    private final Method method;

    public MethodWrapper(Object handle, Method method) {
        super(handle);
        this.method = method;
    }

    public Method getMethod() {
        return method;
    }
}
