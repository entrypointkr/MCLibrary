package kr.rvs.mclibrary.bukkit;

import org.apache.commons.lang.Validate;

/**
 * Created by Junhyeong Lim on 2017-07-28.
 */
public class MCValidate {
    public static void specificPluginEnabled(String pluginName) {
        Validate.isTrue(MCUtils.isEnabled(pluginName),
                pluginName + " 이(가) 활성되어있지 않습니다.");
    }

    public static void protocolLibEnabled() {
        specificPluginEnabled("ProtocolLib");
    }
}
