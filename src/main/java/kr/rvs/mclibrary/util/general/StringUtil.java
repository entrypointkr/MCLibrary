package kr.rvs.mclibrary.util.general;

import org.apache.commons.lang.StringUtils;

/**
 * Created by Junhyeong Lim on 2017-08-12.
 */
public class StringUtil {
    public static int length(String str) {
        if (StringUtils.isEmpty(str))
            return 0;

        double length = 0;
        char[] chars = str.toCharArray();
        for (char ch : chars) {
            if (Character.getType(ch) == 5)
                length += 1.5;
            else
                length += 1;
        }

        return ((Long) Math.round(length)).intValue();
    }
}
