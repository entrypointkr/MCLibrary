package kr.rvs.mclibrary.bukkit.location;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
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

    public static void setDirection(Entity entity, Vector vector) {
        Location entityLoc = entity.getLocation();
        Vector trajectory = getTrajectory(entityLoc.toVector(), vector);
        entityLoc.setYaw(getYaw(trajectory));
        entityLoc.setPitch(getPitch(trajectory));

        entity.teleport(entityLoc);
    }

    public static void setDirection(Entity entity, Location location) {
        setDirection(entity, location.toVector());
    }

    public static Vector getTrajectory(Vector from, Vector to) {
        return to.subtract(from).normalize();
    }

    public static float getPitch(Vector vector) {
        double x = vector.getX();
        double y = vector.getY();
        double z = vector.getZ();
        double xz = Math.sqrt(x * x + z * z);

        return (float) (Math.toDegrees(Math.atan(xz / y))
                        + y <= 0 ? 90 : -90);
    }

    public static float getYaw(Vector vector) {
        double x = vector.getX();
        double z = vector.getZ();

        return (float) Math.toDegrees(Math.atan(-x / z))
                + z < 0 ? 180 : 0;
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
