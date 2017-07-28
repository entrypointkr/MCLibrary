package kr.rvs.mclibrary.util.bukkit.entity;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

/**
 * Created by Junhyeong Lim on 2017-07-28.
 */
public class WrappedEntityFactory {
    public static EnderDragonWrapper createEnderDragon(Location location) {

        Entity entity = location.getWorld().spawnEntity(location, EntityType.ENDER_DRAGON);
        return new EnderDragonWrapper(entity);
    }
}
