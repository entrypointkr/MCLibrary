package kr.rvs.mclibrary.util.bukkit.player;

import kr.rvs.mclibrary.util.general.Wrapper;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Junhyeong Lim on 2017-07-29.
 */
public class PlayerWrapper extends Wrapper<Player> {
    public PlayerWrapper(Player handle) {
        super(handle);
    }

    public int hasItemAmount(ItemStack item) {
        return PlayerUtils.hasItemAmount(getHandle(), item);
    }

    public boolean isHasItem(ItemStack item, int amount) {
        return PlayerUtils.isHasItem(getHandle(), item, amount);
    }

    public boolean takeItem(ItemStack item, int takeAmount) {
        return PlayerUtils.takeItem(getHandle(), item, takeAmount);
    }
}
