package kr.rvs.mclibrary.reflection;

import org.apache.commons.lang3.ArrayUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Optional;

/**
 * Created by Junhyeong Lim on 2017-07-31.
 */
public class Reflections {
    public static Class<?> getSuperClass(Class<?> aClass, String superClassName, boolean contains) {
        Class<?> superClass = aClass.getSuperclass();
        String name = superClass.getName();

        if (superClass == Object.class)
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
        return new FieldEx(null);
    }

    public static MethodEx getMethodEx(Class<?> aClass, String methodName, Class<?>... paramTypes) {
        for (Method method : getAllMethods(aClass)) {
            if (method.getName().equals(methodName)
                    && Arrays.equals(method.getParameterTypes(), paramTypes)) {
                return new MethodEx(method);
            }
        }
        return new MethodEx(null);
    }

    public static ConstructorEx getConstructorEx(Class<?> aClass, Class<?>... parameters) {
        for (Constructor<?> constructor : getAllConstructors(aClass)) {
            if (Arrays.equals(constructor.getParameterTypes(), parameters))
                return new ConstructorEx(constructor);
        }
        return new ConstructorEx(null);
    }

    public static <T extends Annotation> Optional<T> getAnnotation(Class<?> aClass, Class<T> annotationClass) {
        return Optional.ofNullable(aClass.getAnnotation(annotationClass));
    }

    public static <T extends Annotation> Optional<T> getAnnotation(Method method, Class<T> annotClass) {
        return Optional.ofNullable(method.getAnnotation(annotClass));
    }

    public static void crackFinalFieldModifier(Field field) {
        try {
            Field modifierField = Field.class.getDeclaredField("modifiers");
            modifierField.setAccessible(true);
            modifierField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
    }

    private Reflections() {
    }
}
