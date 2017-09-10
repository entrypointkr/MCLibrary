package kr.rvs.mclibrary.general;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

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

    public static List<String> lineBreak(CharSequence content, int count) {
        List<String> ret = new ArrayList<>();
        int length = content.length();
        int line = length / count + (length % count == 0 ? 0 : 1);
        for (int i = 0; i < line; i++) {
            int start = i * count;
            int end = (i + 1) * count;
            ret.add((String) content.subSequence(start,
                    end > length ? length : end));
        }
        return ret;
    }
}
