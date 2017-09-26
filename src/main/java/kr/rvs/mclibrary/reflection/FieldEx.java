package kr.rvs.mclibrary.reflection;

import java.lang.reflect.Field;
import java.util.Optional;

/**
 * Created by Junhyeong Lim on 2017-09-22.
 */
public class FieldEx {
    private Field field;

    public FieldEx() {
    }

    public FieldEx(Field field) {
        this.field = field;
    }

    @SuppressWarnings("unchecked")
    public <T> Optional<T> get(Object object) {
        try {
            return Optional.ofNullable((T) field.get(object));
        } catch (Throwable ignore) {
            // Ignore
        }
        return Optional.empty();
    }
}
