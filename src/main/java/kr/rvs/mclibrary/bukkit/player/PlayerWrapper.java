package kr.rvs.mclibrary.bukkit.player;

import kr.rvs.mclibrary.bukkit.inventory.InventoryUtils;
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
        return InventoryUtils.hasItems(player, item);
    }

    public boolean hasItem(ItemStack item, int amount) {
        return InventoryUtils.hasItem(player, item, amount);
    }

    public boolean takeItem(ItemStack item, int takeAmount) {
        return InventoryUtils.takeItem(player, item, takeAmount);
    }

    public boolean hasSpace(ItemStack item, int amount) {
        return InventoryUtils.hasSpace(player, item, amount);
    }

    public void sendBaseComponent(BaseComponent component) {
        PlayerUtils.sendBaseComponent(player, component);
    }

    public int getMaxHealth() {
        return PlayerUtils.getMaxHealth(player);
    }
}
