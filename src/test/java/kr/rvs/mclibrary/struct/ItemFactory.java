package kr.rvs.mclibrary.struct;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

/**
 * Created by Junhyeong Lim on 2017-09-12.
 */
public class ItemFactory {
    public static final Random RANDOM = new Random();

    public static ItemStack[] createRandomItems() {
        ItemStack[] items = new ItemStack[RANDOM.nextInt(27)];
        for (int i = 0; i < items.length; i++) {
            items[i] = createRandomItem();
        }
        return items;
    }

    public static ItemStack createRandomItem() {
        Material[] materials = Material.values();
        return new ItemStack(materials[RANDOM.nextInt(materials.length)]);
    }
}
