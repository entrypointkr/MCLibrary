package kr.rvs.mclibrary.struct;

import org.bukkit.Bukkit;
import org.bukkit.Server;

/**
 * Created by Junhyeong Lim on 2017-07-26.
 */
public class Injector {
    public static void injectServer(Server server) {
        if (Bukkit.getServer() == null) {
            Bukkit.setServer(server);
        }
    }
}
