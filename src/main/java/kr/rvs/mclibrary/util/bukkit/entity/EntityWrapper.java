package kr.rvs.mclibrary.util.bukkit.entity;

import org.bukkit.entity.Entity;

/**
 * Created by Junhyeong Lim on 2017-07-28.
 */
public class EntityWrapper {
    private final Entity entity;

    public EntityWrapper(Entity entity) {
        this.entity = entity;
    }

    public Entity getEntity() {
        return entity;
    }
}
