package kr.rvs.mclibrary.bukkit.player;

import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

/**
 * Created by Junhyeong Lim on 2017-07-29.
 */
public class PlayerWrapper {
    private final Player player;

    public PlayerWrapper(Player player) {
        this.player = Objects.requireNonNull(player);
    }

    public int hasItems(ItemStack item) {
        return PlayerUtils.hasItems(player, item);
    }

    public boolean isHasItem(ItemStack item, int amount) {
        return PlayerUtils.isHasItem(player, item, amount);
    }

    public boolean takeItem(ItemStack item, int takeAmount) {
        return PlayerUtils.takeItem(player, item, takeAmount);
    }

    public void sendBaseComponent(BaseComponent component) {
        PlayerUtils.sendBaseComponent(player, component);
    }

    public int getMaxHealth() {
        return PlayerUtils.getMaxHealth(player);
    }
}
