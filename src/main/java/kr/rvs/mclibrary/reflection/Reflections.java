package kr.rvs.mclibrary.reflection;

import org.apache.commons.lang.ArrayUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by Junhyeong Lim on 2017-07-31.
 */
public class Reflections {
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

    public static Field[] getAllFields(Class<?> aClass) {
        return (Field[]) ArrayUtils.addAll(aClass.getFields(), aClass.getDeclaredFields());
    }

    public static Method[] getAllMethods(Class<?> aClass) {
        return (Method[]) ArrayUtils.addAll(aClass.getMethods(), aClass.getDeclaredMethods());
    }

    public static Constructor[] getAllConstructors(Class<?> aClass) {
        return (Constructor[]) ArrayUtils.addAll(aClass.getConstructors(), aClass.getDeclaredConstructors());
    }

    public static FieldEx getFieldEx(Class<?> aClass, String fieldName) {
        for (Field field : getAllFields(aClass)) {
            if (field.getName().equals(fieldName)) {
                return new FieldEx(field);
            }
        }
        return new FieldEx();
    }
}
