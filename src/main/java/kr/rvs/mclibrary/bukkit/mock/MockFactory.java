package kr.rvs.mclibrary.bukkit.mock;

import org.bukkit.OfflinePlayer;

import java.lang.reflect.Proxy;

/**
 * Created by Junhyeong Lim on 2017-11-16.
 */
public class MockFactory {
    public static OfflinePlayer createOfflinePlayer(String name) {
        return (OfflinePlayer) Proxy.newProxyInstance(
                ClassLoader.getSystemClassLoader(),
                new Class[]{OfflinePlayer.class},
                (proxy, method, args) -> name
        );
    }
}
