package kr.rvs.mclibrary.bukkit.location;

import kr.rvs.mclibrary.general.NumberUtil;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.util.Vector;

/**
 * Created by Junhyeong Lim on 2017-10-08.
 */
public class LocationUtils {
    public static String toString(Vector vector) {
        return vector != null ?
                String.format("x: %s, y: %s, z: %s", vector.getX(), vector.getY(), vector.getZ()) :
                "null";
    }

    public static Location setDirection(Location location, Vector vector) {
        final double _2PI = 2 * Math.PI;
        final double x = vector.getX();
        final double z = vector.getZ();

        if (x == 0 && z == 0) {
            location.setPitch(vector.getY() > 0 ? -90 : 90);
            return location;
        }

        double theta = Math.atan2(-x, z);
        location.setYaw((float) Math.toDegrees((theta + _2PI) % _2PI));

        double x2 = NumberUtil.square(x);
        double z2 = NumberUtil.square(z);
        double xz = Math.sqrt(x2 + z2);
        location.setPitch((float) Math.toDegrees(Math.atan(-vector.getY() / xz)));

        return location;
    }

    public static Location getTopLocation(Location location) {
        World world = location.getWorld();
        int height = world.getMaxHeight();
        int x = location.getBlockX();
        int z = location.getBlockZ();
        for (int i = height; i >= 0; i--) {
            Block block = world.getBlockAt(x, i, z);
            if (block.getType().isSolid())
                return new Location(world, x, i + 1, z);
        }

        return location;
    }
}
