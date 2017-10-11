package kr.rvs.mclibrary.reflection;

import java.lang.reflect.Method;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * Created by Junhyeong Lim on 2017-09-30.
 */
@SuppressWarnings("unchecked")
public class MethodEx extends AccessibleObjectWrapper<Method> {
    public MethodEx(Method object) {
        super(object);
    }

    public <T> Optional<T> invoke(Object object, Consumer<Exception> callback, Object... args) {
        try {
            return Optional.ofNullable((T) getAccessibleObject().invoke(object, args));
        } catch (Exception ex) {
            callback.accept(ex);
        }
        return Optional.empty();
    }

    public <T> Optional<T> invoke(Object object, Object... args) {
        return invoke(object, e -> {
        }, args);
    }
}
