package kr.rvs.mclibrary.util.bukkit;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import kr.rvs.mclibrary.util.Static;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Junhyeong Lim on 2017-07-28.
 */
public class MCUtils {
    private static String packageVersion;

    public static String getNMSPackageVersion() {
        if (packageVersion == null) {
            try {
                Server server = Bukkit.getServer();
                Field field = server.getClass().getDeclaredField("console");
                field.setAccessible(true);
                Object nmsServer = field.get(server);
                String packageName = nmsServer.getClass().getName();
                int point = packageName.indexOf(".v") + 1;

                packageVersion = packageName.substring(
                        point, packageName.indexOf('.', point));
            } catch (NoSuchFieldException | IllegalAccessException e) {
                Static.log(e);
            }
        }
        return packageVersion;
    }

    public static String getNMSClassName(String className) {
        return "net.minecraft.server." + getNMSPackageVersion() + "." + className;
    }

    public static Class<?> getNMSClass(String name) {
        try {
            return Class.forName(getNMSClassName(name));
        } catch (Exception e) {
            throw new IllegalStateException("Can't find a " + name + " class");
        }
    }

    public static String getOBCClassName(String className) {
        return "org.bukkit.craftbukkit." + getNMSPackageVersion() + "." + className;
    }

    public static Class<?> getOBCClass(String name) {
        try {
            return Class.forName(getOBCClassName(name));
        } catch (Exception ex) {
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

    public static String colorize(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static void main(String[] args) {
        String name = "net.minecraft.server.v1_12_R1.DedicatedServer";
        int point = name.indexOf(".v") + 1;
        System.out.println(name.substring(point, name.indexOf('.', point)));
    }
}
