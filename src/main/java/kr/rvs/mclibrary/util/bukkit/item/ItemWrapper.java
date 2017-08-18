package kr.rvs.mclibrary.util.bukkit.item;

import kr.rvs.mclibrary.util.general.Wrapper;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Junhyeong Lim on 2017-07-27.
 */
public class ItemWrapper extends Wrapper<ItemStack> {
    public ItemWrapper(ItemStack handle) {
        super(handle);
    }

    public ItemWrapper setLore(String... lores) {
        ItemMeta meta = getHandle().getItemMeta();
        meta.setLore(Arrays.asList(lores));
        getHandle().setItemMeta(meta);

        return this;
    }

    public ItemWrapper addLore(String... lores) {
        ItemMeta meta = getHandle().getItemMeta();
        List<String> loreList = meta.getLore();
        loreList.addAll(Arrays.asList(lores));
        getHandle().setItemMeta(meta);

        return this;
    }

    public ItemWrapper setName(String displayName) {
        ItemMeta meta = getHandle().getItemMeta();
        meta.setDisplayName(displayName);
        getHandle().setItemMeta(meta);

        return this;
    }
}
