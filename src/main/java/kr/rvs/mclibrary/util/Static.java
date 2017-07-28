package kr.rvs.mclibrary.util;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Junhyeong Lim on 2017-07-26.
 */
public class Static {
    public static void log(Throwable ex) {
        Logger.getGlobal().log(Level.WARNING, "[MCLibrary] 에러가 발생했습니다.", ex);
    }
}
