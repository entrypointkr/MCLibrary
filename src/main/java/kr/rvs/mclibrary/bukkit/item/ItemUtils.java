package kr.rvs.mclibrary.bukkit.item;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Junhyeong Lim on 2017-09-11.
 */
public class ItemUtils {
    public static boolean isEmpty(ItemStack item) {
        return item == null || item.getType() == Material.AIR;
    }
}
