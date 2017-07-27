package kr.rvs.mclibrary.util.bukkit.item;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Junhyeong Lim on 2017-07-27.
 */
public class ItemWrapper {
    private final ItemStack itemStack;

    public ItemWrapper(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public ItemWrapper setLore(String... lores) {
        ItemMeta meta = itemStack.getItemMeta();
        meta.setLore(Arrays.asList(lores));
        itemStack.setItemMeta(meta);

        return this;
    }

    public ItemWrapper addLore(String... lores) {
        ItemMeta meta = itemStack.getItemMeta();
        List<String> loreList = meta.getLore();
        loreList.addAll(Arrays.asList(lores));
        itemStack.setItemMeta(meta);

        return this;
    }

    public ItemWrapper setName(String displayName) {
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(displayName);
        itemStack.setItemMeta(meta);

        return this;
    }
}
