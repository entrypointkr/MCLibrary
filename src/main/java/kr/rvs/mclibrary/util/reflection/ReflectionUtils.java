package kr.rvs.mclibrary.util.reflection;

import org.apache.commons.lang.ArrayUtils;

import java.lang.reflect.Field;

/**
 * Created by Junhyeong Lim on 2017-07-31.
 */
public class ReflectionUtils {
    public static Class<?> getSuperClass(Class<?> aClass, String superClassName, boolean contains) {
        Class<?> superClass = aClass.getSuperclass();
        String name = superClass.getName();

        if (superClass == null || superClass == Object.class)
            return null;

        if (contains ?
                name.contains(superClassName) :
                name.endsWith(superClassName)) {
            return superClass;
        } else {
            return getSuperClass(superClass, superClassName, contains);
        }
    }

    public static Class<?> getSuperClass(Class<?> aClass, String superClassName) {
        return getSuperClass(aClass, superClassName, false);
    }

    public static Field[] getFields(Class<?> aClass) {
        return (Field[]) ArrayUtils.addAll(aClass.getDeclaredFields(), aClass.getFields());
    }
}
