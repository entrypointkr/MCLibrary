package kr.rvs.mclibrary.util.bukkit;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import kr.rvs.mclibrary.util.Static;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Junhyeong Lim on 2017-07-28.
 */
public class MCUtils {
    private static String nmsPackageName;

    public static synchronized String getNMSPackage(String name) {
        if (nmsPackageName == null) {
            try {
                Server server = Bukkit.getServer();
                Field field = server.getClass().getDeclaredField("console");
                field.setAccessible(true);
                Object nmsServer = field.get(server);
                String packageName = nmsServer.getClass().getName();
                nmsPackageName = packageName.substring(0,
                        packageName.lastIndexOf("."));
            } catch (NoSuchFieldException | IllegalAccessException e) {
                Static.log(e);
            }
        }

        return nmsPackageName + "." + name;
    }

    public static Class<?> getNMSClass(String name) {
        try {
            return Class.forName(getNMSPackage(name));
        } catch (Exception e) {
            throw new IllegalStateException("Can't find a " + name + " class");
        }
    }

    public static Object getHandle(Object object) {
        try {
            Method method = object.getClass().getMethod("getHandle");
            return method.invoke(object);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            Static.log(e);
            throw new IllegalArgumentException(object.getClass().getName());
        }
    }

    public static ProtocolManager getProtocolManager() {
        MCValidate.isProtocolLibEnabled();

        return ProtocolLibrary.getProtocolManager();
    }

    public static void sendPacket(Player player, PacketContainer packet) {
        try {
            getProtocolManager().sendServerPacket(player, packet);
        } catch (InvocationTargetException e) {
            Static.log(e);
        }
    }
}
