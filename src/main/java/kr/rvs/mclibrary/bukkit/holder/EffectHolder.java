package kr.rvs.mclibrary.bukkit.holder;

import kr.rvs.mclibrary.bukkit.MCUtils;
import kr.rvs.mclibrary.reflection.MethodEx;
import kr.rvs.mclibrary.reflection.Reflections;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Junhyeong Lim on 2017-10-11.
 */
public class EffectHolder {
    private Effect effect;
    private int data;
    private int radius;

    private static int toData(Effect effect, Object data) {
        AtomicInteger ret = new AtomicInteger();
        MethodEx method = Reflections.getMethodEx(MCUtils.getOBCClass("CraftEffect"), "getDataValue");
        method.<Integer>invoke(null, effect, data).ifPresent(ret::set);
        return ret.get();
    }

    public EffectHolder(Effect effect, int data, int radius) {
        this.effect = effect;
        this.data = data;
        this.radius = radius;
    }

    public EffectHolder(Effect effect, int data) {
        this(effect, data, 64);
    }

    public EffectHolder(Effect effect, Object object) {
        this(effect, toData(effect, object));
    }

    public EffectHolder(Effect effect) {
        this(effect, 0);
    }

    public void play(Location location) {
        location.getWorld().playEffect(location, effect, data, radius);
    }

    @SuppressWarnings("deprecation")
    public void play(Location location, Iterable<Player> players) {
        for (Player player : players) {
            player.playEffect(location.add(0.5, 0, 0.5), effect, data);
        }
    }

    public void play(Location location, Player... players) {
        play(location, Arrays.asList(players));
    }

    public void play(Iterable<Player> players) {
        for (Player player : players) {
            play(player.getLocation(), player);
        }
    }

    public Effect getEffect() {
        return effect;
    }

    public void setEffect(Effect effect) {
        this.effect = effect;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }
}
