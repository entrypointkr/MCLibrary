package kr.rvs.mclibrary.bukkit.location;

import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

/**
 * Created by Junhyeong Lim on 2017-10-07.
 */
public class Region {
    private final String world;
    private final Vector min;
    private final Vector max;

    public Region(Location pointA, Location pointB) {
        Validate.isTrue(pointA.getWorld().equals(pointB.getWorld()), "Two worlds are different.");

        double minX = Math.min(pointA.getBlockX(), pointB.getBlockX());
        double maxX = Math.max(pointA.getBlockX(), pointB.getBlockX()) + 1;
        double minY = Math.min(pointA.getBlockY(), pointB.getBlockY());
        double maxY = Math.max(pointA.getBlockY(), pointB.getBlockY());
        double minZ = Math.min(pointA.getBlockZ(), pointB.getBlockZ());
        double maxZ = Math.max(pointA.getBlockZ(), pointB.getBlockZ()) + 1;

        pointA.setX(minX);
        pointA.setY(minY);
        pointA.setZ(minZ);
        pointB.setX(maxX);
        pointB.setY(maxY);
        pointB.setZ(maxZ);

        this.world = pointA.getWorld().getName();
        this.min = pointA.toVector();
        this.max = pointB.toVector();
    }

    public boolean isIn(Location location) {
        World world = location.getWorld();
        double x = location.getX();
        double y = location.getY();
        double z = location.getZ();
        return world.getName().equals(this.world)
                && min.getX() <= x && max.getX() >= x
                && min.getY() <= y && max.getY() >= y
                && min.getZ() <= z && max.getZ() >= z;
    }

    public boolean isIn(Entity entity) {
        return isIn(entity.getLocation());
    }

    public Location getCenter() {
        double diffX = max.getX() - min.getX();
        double diffY = max.getY() - min.getY();
        double diffZ = max.getZ() - min.getZ();

        return new Location(
                getWorld(),
                min.getX() + diffX / 2,
                min.getY() + diffY / 2,
                min.getZ() + diffZ / 2
        );
    }

    public World getWorld() {
        return Bukkit.getWorld(world);
    }
}
