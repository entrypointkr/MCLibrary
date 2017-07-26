package kr.rvs.mclibrary.util;

/**
 * Created by Junhyeong Lim on 2017-07-26.
 */
public class Static {
    public static void log(Throwable ex) {
        System.out.println(Lang.PREFIX + "에러가 발생했습니다. " + ex.toString());
        ex.printStackTrace();
    }
}
