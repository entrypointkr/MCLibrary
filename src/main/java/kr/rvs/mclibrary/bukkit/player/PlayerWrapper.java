package kr.rvs.mclibrary.bukkit.player;

import kr.rvs.mclibrary.Static;
import kr.rvs.mclibrary.bukkit.MCUtils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by Junhyeong Lim on 2017-07-29.
 */
public class PlayerWrapper extends AtomicReference<Player> {
    public PlayerWrapper(Player handle) {
        super(handle);
    }

    public int hasItemAmount(ItemStack item) {
        return PlayerUtils.hasItemAmount(get(), item);
    }

    public boolean isHasItem(ItemStack item, int amount) {
        return PlayerUtils.isHasItem(get(), item, amount);
    }

    public boolean takeItem(ItemStack item, int takeAmount) {
        return PlayerUtils.takeItem(get(), item, takeAmount);
    }

    public ContainerWrapper getContainer() {
        Object internalPlayer = MCUtils.getHandle(get());
        ContainerWrapper ret = null;
        try {
            Field containerField = internalPlayer.getClass().getField("activeContainer");
            ret = new ContainerWrapper(containerField.get(internalPlayer));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            Static.log(e);
        }

        return ret;
    }
}
