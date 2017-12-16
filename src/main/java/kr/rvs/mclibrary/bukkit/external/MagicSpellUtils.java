package kr.rvs.mclibrary.bukkit.external;

/**
 * Created by Junhyeong Lim on 2017-10-11.
 */
public class MagicSpellUtils {
    public static boolean isCalledByMagicSpell() {
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        for (StackTraceElement element : elements) {
            if (element.getClassName().startsWith("com.nisovin.magicspells."))
                return true;
        }
        return false;
    }

    private MagicSpellUtils() {
    }
}
