package kr.rvs.mclibrary.reflection;

import java.lang.reflect.Field;
import java.util.Optional;

/**
 * Created by Junhyeong Lim on 2017-09-22.
 */
public class FieldEx extends AccessibleObjectWrapper<Field> {
    public FieldEx(Field field) {
        super(field);
    }

    @SuppressWarnings("unchecked")
    public <T> Optional<T> get(Object object) {
        try {
            return Optional.ofNullable((T) getAccessibleObject().get(object));
        } catch (Throwable ignore) {
            // Ignore
        }
        return Optional.empty();
    }
}
