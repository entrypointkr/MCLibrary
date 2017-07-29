package kr.rvs.mclibrary.util.bukkit.player;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Junhyeong Lim on 2017-07-29.
 */
public class PlayerWrapper {
    private final Player player;

    public PlayerWrapper(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public int hasItemAmount(ItemStack item) {
        return PlayerUtils.hasItemAmount(player, item);
    }

    public boolean isHasItem(ItemStack item, int amount) {
        return PlayerUtils.isHasItem(player, item, amount);
    }

    public boolean takeItem(ItemStack item, int takeAmount) {
        return PlayerUtils.takeItem(player, item, takeAmount);
    }
}
