package kr.rvs.mclibrary.bukkit.world;

import org.bukkit.Bukkit;
import org.bukkit.World;

import java.util.Optional;

/**
 * Created by Junhyeong Lim on 2017-11-06.
 */
public class WorldUtils {
    public static Optional<World> getWorld(String name) {
        return Optional.ofNullable(Bukkit.getWorld(name));
    }
}
