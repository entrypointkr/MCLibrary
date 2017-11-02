package kr.rvs.mclibrary.bukkit.player;

import kr.rvs.mclibrary.Static;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * Created by Junhyeong Lim on 2017-07-28.
 */
public class PlayerUtils {
    private static BaseComponentSender COMPONENT_SENDER = (player, component) -> player.spigot().sendMessage(component);

    static {
        try {
            Player.class.getDeclaredMethod("spigot");
        } catch (NoSuchMethodException e) {
            COMPONENT_SENDER = ((player, component) ->
                    Bukkit.dispatchCommand(player, "tellraw " + ComponentSerializer.toString(component)));
        }
    }

    public static Collection<? extends Player> getOnlinePlayers() {
        try {
            return Bukkit.getOnlinePlayers();
        } catch (NoSuchMethodError th) {
            List<Player> onlinePlayers = new ArrayList<>();
            try {
                Method method = Bukkit.class.getMethod("getOnlinePlayers");
                onlinePlayers.addAll(Arrays.asList((Player[]) method.invoke(null)));
            } catch (Exception e) {
                Static.log(e);
            }

            return onlinePlayers;
        }
    }

    public static Optional<Player> getPlayerOptional(String name) {
        return Optional.ofNullable(Bukkit.getPlayer(name));
    }

    public static Optional<Player> getPlayerOptional(UUID uuid) {
        return Optional.ofNullable(Bukkit.getPlayer(uuid));
    }

    public static int getMaxHealth(Player player) {
        try {
            Method maxHealthMethod = Damageable.class.getMethod("getMaxHealth");
            return ((Number) maxHealthMethod.invoke(player)).intValue();
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
    }

    public static void setMaxHealth(Player player, int maxHealth) {
        Optional<Method> setMaxHealthMethod = Stream.of(Damageable.class.getDeclaredMethods())
                .filter(method -> method.getName().equals("setMaxHealth"))
                .findFirst();
        setMaxHealthMethod.ifPresent(method -> {
            try {
                method.invoke(player, maxHealth);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new IllegalStateException(e);
            }
        });
    }

    public static void sendBaseComponent(Player player, BaseComponent component) {
        COMPONENT_SENDER.send(player, component);
    }

    public interface BaseComponentSender {
        void send(Player player, BaseComponent component);
    }
}
