package kr.rvs.mclibrary.util.bukkit.entity;

import kr.rvs.mclibrary.util.Static;
import kr.rvs.mclibrary.util.bukkit.MCUtils;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Junhyeong Lim on 2017-07-28.
 */
public class EnderDragonWrapper extends EntityWrapper {
    private final Object handle;

    public EnderDragonWrapper(Entity entity) {
        super(entity);
        this.handle = MCUtils.getHandle(entity);
    }

    public void setLocation(Location location) {
        Method setLocationMethod = getMethod("setLocation",
                double.class, double.class, double.class, float.class, float.class);
        invoke(setLocationMethod, location.getX(), location.getY(), location.getZ(),
                location.getYaw(), location.getPitch());
    }

    private Method getMethod(String methodName, Class<?>... parameterTypes) {
        try {
            return handle.getClass().getMethod(methodName, parameterTypes);
        } catch (NoSuchMethodException e) {
            Static.log(e);
            throw new IllegalStateException(e);
        }
    }

    private void invoke(Method method, Object... args) {
        try {
            method.invoke(handle, args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            Static.log(e);
            throw new IllegalStateException(e);
        }
    }
}
