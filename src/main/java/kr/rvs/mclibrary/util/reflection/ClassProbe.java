package kr.rvs.mclibrary.util.reflection;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created by Junhyeong Lim on 2017-07-26.
 */
@SuppressWarnings("unchecked")
public class ClassProbe {
    private final String packageName;
    private final String rscPackageName;
    private final String winPackageName;
    private final List<Class<?>> cachedClasses = new ArrayList<>();

    public ClassProbe(String packageName, ClassLoader... loaders) {
        this.packageName = packageName;
        this.rscPackageName = packageName
                .replace('.', '/')
                .replace(".class", "");
        this.winPackageName = packageName
                .replace('.', '\\')
                .replace(".class", "");
        init(loaders);
    }

    public ClassProbe(String packageName) {
        this(packageName, getDefaultClassLoaders());
    }

    public static ClassLoader[] getDefaultClassLoaders() {
        return new ClassLoader[]{
                Thread.currentThread().getContextClassLoader(),
                ClassProbe.class.getClassLoader()
        };
    }

    private void init(ClassLoader... loaders) {
        for (ClassLoader loader : loaders) {
            if (loader == null)
                continue;

            try {
                Enumeration<URL> urlEnum = loader.getResources(rscPackageName);
                while (urlEnum.hasMoreElements()) {
                    cachedClasses.addAll(
                            getClassesFromUrl(urlEnum.nextElement()));
                }
            } catch (IOException e) {
                // Ignore
            }
        }
    }

    private Set<Class<?>> getClassesFromUrl(URL url) throws UnsupportedEncodingException {
        Set<Class<?>> ret = new HashSet<>();

        if (url.getProtocol().equals("jar")) {
            ZipEntry entry;
            try (ZipInputStream in = new ZipInputStream(new FileInputStream(getJarFileFromUrl(url)))) {
                while ((entry = in.getNextEntry()) != null) {
                    String name = entry.getName();

                    if (name.endsWith(".class") && name.startsWith(rscPackageName)) {
                        String className = name.replace('/', '.');
                        ret.add(Class.forName(className.substring(0, className.indexOf(".class"))));
                    }
                }
            } catch (Exception ex) {
                // Ignore
            }
        } else {
            try {
                ret.addAll(getClassesFromDirectory(new File(url.toURI()).listFiles()));
            } catch (Exception e) {
                // Ignore
            }
        }

        return ret;
    }

    private File getJarFileFromUrl(URL url) throws MalformedURLException, URISyntaxException {
        String path = url.getPath();
        path = path.substring(0, path.indexOf('!'));

        return new File(new URL(path).toURI());
    }

    private Set<Class<?>> getClassesFromDirectory(File[] files) throws ClassNotFoundException {
        Set<Class<?>> ret = new HashSet<>();

        if (files == null)
            return ret;

        for (File aFile : files) {
            if (aFile.isFile()) {
                String path = aFile.getPath();
                int start = path.indexOf(winPackageName);
                int end = path.indexOf(".class");

                if (start == -1 || end == -1)
                    continue;

                String className = path.substring(start, end).replace("\\", ".");
                ret.add(Class.forName(className));
            } else {
                ret.addAll(getClassesFromDirectory(aFile.listFiles()));
            }
        }

        return ret;
    }

    public <T> List<Class<? extends T>> getSubTypesOf(Class<T> tClass) {
        List<Class<? extends T>> ret = new ArrayList<>();
        for (Class<?> aClass : cachedClasses) {
            if (tClass.isAssignableFrom(aClass)) {
                ret.add((Class<? extends T>) aClass);
            }
        }
        return ret;
    }

    public List<Class<?>> getCachedClasses() {
        return cachedClasses;
    }
}
