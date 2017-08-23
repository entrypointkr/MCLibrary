package kr.rvs.mclibrary.util.general;

import java.io.File;
import java.io.IOException;

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
}
