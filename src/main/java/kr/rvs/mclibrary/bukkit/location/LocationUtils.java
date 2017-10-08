package kr.rvs.mclibrary.bukkit.location;

import org.bukkit.util.Vector;

/**
 * Created by Junhyeong Lim on 2017-10-08.
 */
public class LocationUtils {
    public static String toString(Vector vector) {
        return vector != null ?
                String.format("x: %s, y: %s, z: %s", vector.getX(), vector.getY(), vector.getZ()):
                "null";
    }
}
