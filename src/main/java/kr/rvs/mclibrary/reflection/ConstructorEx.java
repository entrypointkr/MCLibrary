package kr.rvs.mclibrary.reflection;

import java.lang.reflect.Constructor;
import java.util.Optional;

/**
 * Created by Junhyeong Lim on 2017-09-28.
 */
public class ConstructorEx extends AccessibleObjectWrapper<Constructor> {
    public ConstructorEx(Constructor constructor) {
        super(constructor);
    }

    public Optional<Object> newInstance(Object... args) {
        try {
            return Optional.of(getAccessibleObject().newInstance(args));
        } catch (Exception e) {
            // Ignore
        }
        return Optional.empty();
    }
}
