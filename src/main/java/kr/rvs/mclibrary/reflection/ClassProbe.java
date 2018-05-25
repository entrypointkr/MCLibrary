package kr.rvs.mclibrary.reflection;

import kr.rvs.mclibrary.Static;

import java.io.File;
import java.io.FileInputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created by Junhyeong Lim on 2017-09-22.
 */
@SuppressWarnings("unchecked")
public class ClassProbe {
    private static final Set<String> IGNORE_PREFIXES = new HashSet<>();
    private final File file;
    private final InternalStorage storage = new InternalStorage();

    public static void addIgnorePrefix(String prefix) {
        IGNORE_PREFIXES.add(prefix);
    }

    public ClassProbe(File file) {
        this.file = file;
        init();
    }

    private void findClassAndAdd(String name) {
        try {
            String className = name.substring(0, name.indexOf(".class")).replace('/', '.');
            if (IGNORE_PREFIXES.stream().noneMatch(className::startsWith)) {
                Class aClass = Class.forName(className);
                storage.add(aClass);
            }
        } catch (Throwable th) { // NoClassDefFoundError
            // Ignore
        }
    }

    private void init() {
        ZipEntry entry;
        try (ZipInputStream zin = new ZipInputStream(new FileInputStream(file))) {
            while ((entry = zin.getNextEntry()) != null) {
                String name = entry.getName();
                if (name.endsWith(".class")) {
                    findClassAndAdd(name);
                }
            }
        } catch (Exception e) {
            Static.log(e);
        }
    }

    public <T> Set<Class<? extends T>> getSubTypesOf(Class<T> tClass) {
        Set<Class<? extends T>> ret = new HashSet<>();
        for (Class<?> aClass : storage.classes) {
            if (tClass.isAssignableFrom(aClass)) {
                ret.add((Class<? extends T>) aClass);
            }
        }
        return ret;
    }

    public Set<Class<?>> getTypesAnnotatedWith(Class<? extends Annotation> annotCls) {
        return storage.classes.stream()
                .filter(aClass -> aClass.isAnnotationPresent(annotCls))
                .collect(Collectors.toSet());
    }

    public Set<Method> getMethodsAnnotatedWith(Class<? extends Annotation> annotCls) {
        return storage.methods.stream()
                .filter(method -> method.isAnnotationPresent(annotCls))
                .collect(Collectors.toSet());
    }

    class InternalStorage {
        private final Set<Class<?>> classes = new HashSet<>();
        private final Set<Method> methods = new HashSet<>();
        private final Set<Field> fields = new HashSet<>();

        public void add(Class<?> aClass) {
            classes.add(aClass);
            methods.addAll(Arrays.asList(Reflections.getAllMethods(aClass)));
            fields.addAll(Arrays.asList(Reflections.getAllFields(aClass)));
        }
    }
}
