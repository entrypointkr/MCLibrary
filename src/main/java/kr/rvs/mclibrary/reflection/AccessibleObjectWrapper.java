package kr.rvs.mclibrary.reflection;

import java.lang.reflect.AccessibleObject;

/**
 * Created by Junhyeong Lim on 2017-09-28.
 */
public class AccessibleObjectWrapper<T extends AccessibleObject> {
    private final T object;

    public AccessibleObjectWrapper(T object) {
        this.object = object;
        object.setAccessible(true);
    }

    public T getAccessibleObject() {
        return object;
    }
}
