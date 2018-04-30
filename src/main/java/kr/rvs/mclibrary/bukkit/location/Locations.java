package kr.rvs.mclibrary.bukkit.location;

import kr.rvs.mclibrary.general.Numbers;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * Created by Junhyeong Lim on 2017-10-08.
 */
public class Locations {
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

        double x2 = Numbers.square(x);
        double z2 = Numbers.square(z);
        double xz = Math.sqrt(x2 + z2);
        location.setPitch((float) Math.toDegrees(Math.atan(-vector.getY() / xz)));

        return location;
    }

    public static Optional<Location> getLocationTopToBottom(Location location, Predicate<Block> filter) {
        World world = location.getWorld();
        int height = world.getMaxHeight();
        int x = location.getBlockX();
        int z = location.getBlockZ();
        for (int i = height; i >= 0; i--) {
            Block block = world.getBlockAt(x, i, z);
            if (filter.test(block)) {
                return Optional.of(new Location(world, x, i + 1, z));
            }
        }
        return Optional.empty();
    }

    public static Optional<Location> getTopLocation(Location location) {
        return getLocationTopToBottom(location, block -> block.getType().isSolid());
    }

    public static Optional<Location> getLocationBottomToTop(Location location, Predicate<Block> filter) {
        World world = location.getWorld();
        int maxHeight = world.getMaxHeight();
        int x = location.getBlockX();
        int z = location.getBlockZ();
        for (int i = location.getBlockY(); i < maxHeight; i++) {
            Block block = world.getBlockAt(x, i, z);
            if (filter.test(block)) {
                return Optional.of(new Location(world, x, i, z));
            }
        }
        return Optional.empty();
    }

    public static Optional<Location> getEmptyLocation(Location location, Predicate<Block> filter) {
        return getLocationBottomToTop(location, block -> block.getType() == Material.AIR && filter.test(block));
    }

    public static Optional<Location> getEmptyLocation(Location location) {
        return getLocationBottomToTop(location, block -> block.getType() == Material.AIR);
    }

    public static Vector toBlockVector(Vector vector) {
        return new Vector(vector.getBlockX(), vector.getBlockY(), vector.getBlockZ());
    }

    public static Vector toBlockVector(Location location) {
        return toBlockVector(location.toVector());
    }

    public static List<Location> getRelatives(Location location, int radius, Predicate<Location> predicate) {
        List<Location> locations = new ArrayList<>((int) Math.pow(radius * 2 + 1D, 2));
        World world = location.getWorld();
        int blockX = location.getBlockX();
        int blockY = location.getBlockY();
        int blockZ = location.getBlockZ();
        for (int x = blockX - radius; x <= blockX + radius; x++) {
            for (int y = blockY - radius; y <= blockY + radius; y++) {
                for (int z = blockZ - radius; z <= blockZ + radius; z++) {
                    Location newLocation = new Location(world, x, y, z);
                    if (predicate.test(newLocation)) {
                        locations.add(newLocation);
                    }
                }
            }
        }
        return locations;
    }

    public static List<Location> getRelatives(Location location, int radius) {
        return getRelatives(location, radius, aLocation -> true);
    }

    public static List<Location> getRelativesWithoutAir(Location block, int radius) {
        return getRelatives(block, radius, aLocation -> aLocation.getBlock() != null && aLocation.getBlock().getType() != Material.AIR);
    }

    public static boolean equalsBlockLocation(Location locA, Location locB) {
        return locA != null && locB != null
                && locA.getWorld().equals(locB.getWorld())
                && toBlockVector(locA).equals(toBlockVector(locB));
    }

    private Locations() {
    }
}
