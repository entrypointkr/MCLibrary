package kr.rvs.mclibrary.util.general;

import java.io.File;
import java.io.IOException;

/**
 * Created by Junhyeong Lim on 2017-08-23.
 */
public class FileUtils {
    public static void ensure(File file) throws IOException {
        file.getParentFile().mkdirs();
        file.createNewFile();
    }
}
