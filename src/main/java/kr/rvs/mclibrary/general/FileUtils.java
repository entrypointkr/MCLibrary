package kr.rvs.mclibrary.general;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.function.Consumer;

/**
 * Created by Junhyeong Lim on 2017-08-23.
 */
public class FileUtils {
    public static boolean ensure(File file) {
        boolean ret = false;
        if (file.getParentFile().mkdirs()) {
            try {
                ret = file.createNewFile();
            } catch (IOException e) {
                // Ignore
            }
        }

        return ret;
    }

    public static String getNameWithoutExtension(File file) {
        String name = file.getName();
        int point = name.indexOf('.');
        return point >= 0 ? name.substring(0, point) : name;
    }

    public static String getExtension(File file) {
        String name = file.getName();
        int point = name.indexOf('.');
        return point >= 0 ? name.substring(point, name.length()) : "";
    }

    public static File appendToName(File file, String text, String extension) {
        return new File(file.getParentFile(), getNameWithoutExtension(file) + text + extension);
    }

    public static File appendToName(File file, String text) {
        return appendToName(file, text, getNameWithoutExtension(file));
    }

    public static void write(Writer writer, CharSequence charSequence, Consumer<IOException> handler) {
        try {
            writer.write(charSequence.toString());
        } catch (IOException e) {
            handler.accept(e);
        }
    }

    public static void write(Writer writer, CharSequence charSequence) {
        write(writer, charSequence, Exception::printStackTrace);
    }

    public static void write(File file, CharSequence charSequence, Consumer<IOException> handler) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            write(writer, charSequence, handler);
        } catch (IOException e) {
            handler.accept(e);
        }
    }

    public static void write(File file, CharSequence charSequence) {
        write(file, charSequence, Exception::printStackTrace);
    }

    private FileUtils() {
    }
}
