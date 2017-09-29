package kr.rvs.mclibrary.reflection;

import kr.rvs.mclibrary.Static;

import java.lang.reflect.Method;
import java.util.function.Consumer;

/**
 * Created by Junhyeong Lim on 2017-09-30.
 */
@SuppressWarnings("unchecked")
public class MethodEx extends AccessibleObjectWrapper<Method> {
    public MethodEx(Method object) {
        super(object);
    }

    public <T> T invoke(Object object, Consumer<Exception> callback, Object... args) {
        try {
            return (T) getAccessibleObject().invoke(object, args);
        } catch (Exception ex) {
            callback.accept(ex);
        }
        return null;
    }

    public <T> T invoke(Object object, Object... args) {
        return invoke(object, Static::log, args);
    }
}
