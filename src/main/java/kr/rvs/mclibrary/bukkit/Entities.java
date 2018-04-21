package kr.rvs.mclibrary.bukkit;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Projectile;
import org.bukkit.projectiles.ProjectileSource;

/**
 * Created by JunHyeong Lim on 2018-04-21
 */
public class Entities {
    public static Entity swapIfProjectile(Entity entity) {
        if (entity instanceof Projectile) {
            ProjectileSource source = ((Projectile) entity).getShooter();
            if (source instanceof Entity) {
                entity = (Entity) source;
            }
        }
        return entity;
    }
}
