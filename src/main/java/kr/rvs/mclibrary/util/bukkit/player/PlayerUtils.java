package kr.rvs.mclibrary.util.bukkit.player;

import kr.rvs.mclibrary.util.Static;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Created by Junhyeong Lim on 2017-07-28.
 */
public class PlayerUtils {
    public static Collection<? extends Player> getOnlinePlayers() {
        try {
            return Bukkit.getOnlinePlayers();
        } catch (NoSuchMethodError th) {
            List<Player> onlinePlayers = new ArrayList<>();
            try {
                Method method = Bukkit.class.getMethod("getOnlinePlayers");
                onlinePlayers.addAll(Arrays.asList((Player[]) method.invoke(null)));
            } catch (Exception e) {
                Static.log(e);
            }

            return onlinePlayers;
        }
    }
}
