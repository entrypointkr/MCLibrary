package kr.rvs.mclibrary.util.bukkit.entity;

import kr.rvs.mclibrary.util.Static;
import kr.rvs.mclibrary.util.bukkit.MCUtils;
import org.bukkit.World;

import java.lang.reflect.Constructor;

/**
 * Created by Junhyeong Lim on 2017-07-28.
 */
public class NMSEntityFactory {
    public static Object createEnderDragon(World world) {
        Object internalWorld = MCUtils.getHandle(world);
        try {
            Constructor conzt = MCUtils.getNMSClass("EntityEnderDragon").getConstructor(internalWorld.getClass().getSuperclass());
            return conzt.newInstance(internalWorld);
        } catch (Exception ex) {
            Static.log(ex);
            throw new IllegalStateException(ex);
        }
    }
}
