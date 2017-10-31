package kr.rvs.mclibrary.bukkit;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import kr.rvs.mclibrary.Static;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Junhyeong Lim on 2017-07-28.
 */
public class MCUtils {
    private static String packageVersion;

    public static String getBukkitInternalPackageVersion() {
        if (packageVersion == null) {
            String packageName = Bukkit.getServer().getClass().getName();
            int point = packageName.indexOf(".v") + 1;

            packageVersion = packageName.substring(
                    point, packageName.indexOf('.', point));
        }
        return packageVersion;
    }

    public static String getNMSClassName(String className) {
        return "net.minecraft.server." + getBukkitInternalPackageVersion() + "." + className;
    }

    public static Class<?> getNMSClass(String name) {
        try {
            return Class.forName(getNMSClassName(name));
        } catch (Exception e) {
            throw new IllegalStateException("Can't find a " + name + " class");
        }
    }

    public static String getOBCClassName(String className) {
        return "org.bukkit.craftbukkit." + getBukkitInternalPackageVersion() + "." + className;
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
        MCValidate.protocolLibEnabled();

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

    public static List<String> colorize(List<String> list) {
        List<String> newList = new ArrayList<>();
        for (String element : list) {
            newList.add(colorize(element));
        }

        return newList;
    }

    public static List<String> asColorizeList(String... args) {
        for (int i = 0; i < args.length; i++) {
            args[i] = colorize(args[i]);
        }
        return Arrays.asList(args);
    }

    public static boolean isEnabled(String name) {
        return Bukkit.getPluginManager().isPluginEnabled(name);
    }
}
