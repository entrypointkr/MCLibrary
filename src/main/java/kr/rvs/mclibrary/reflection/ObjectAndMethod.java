package kr.rvs.mclibrary.reflection;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * Created by Junhyeong Lim on 2017-09-30.
 */
public class ObjectAndMethod {
    private final Object object;
    private final Method method;

    public ObjectAndMethod(Object object, Method method) {
        this.object = Objects.requireNonNull(object);
        this.method = Objects.requireNonNull(method);
    }

    public Object invoke(Object... args) {
        return new MethodEx(method).invoke(object, args);
    }

    public Object getObject() {
        return object;
    }

    public Method getMethod() {
        return method;
    }
}
