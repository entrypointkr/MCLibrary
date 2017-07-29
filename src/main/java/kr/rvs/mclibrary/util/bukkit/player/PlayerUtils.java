package kr.rvs.mclibrary.util.bukkit.player;

import kr.rvs.mclibrary.util.Static;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Junhyeong Lim on 2017-07-28.
 */
public class PlayerUtils {
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

    public static int hasItemAmount(Player player, ItemStack item) {
        int hasAmount = 0;
        for (ItemStack elemItem : player.getInventory()) {
            if (elemItem.isSimilar(item)) {
                hasAmount += elemItem.getAmount();
            }
        }

        return hasAmount;
    }

    public static boolean isHasItem(Player player, ItemStack item, int amount) {
        int hasAmount = 0;
        for (ItemStack elemItem : player.getInventory()) {
            if (elemItem.isSimilar(item)) {
                hasAmount += elemItem.getAmount();

                if (hasAmount >= amount)
                    return true;
            }
        }

        return false;
    }

    public static boolean takeItem(Player player, ItemStack item, int takeAmount) {
        Inventory inv = player.getInventory();
        Set<ItemStack> removeSet = new HashSet<>();

        for (ItemStack elemItem : inv) {
            if (!elemItem.isSimilar(item))
                continue;

            removeSet.add(elemItem);
            int amount = elemItem.getAmount();

            if (takeAmount > amount) {
                takeAmount -= amount;
            } else {
                for (ItemStack remove : removeSet) {
                    inv.removeItem(remove);
                }
                return true;
            }
        }

        return false;
    }
}
