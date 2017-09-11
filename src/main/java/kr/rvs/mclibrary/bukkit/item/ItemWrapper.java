package kr.rvs.mclibrary.bukkit.item;

import kr.rvs.mclibrary.bukkit.MCUtils;
import kr.rvs.mclibrary.general.Wrapper;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static kr.rvs.mclibrary.bukkit.MCUtils.asColorizeList;

/**
 * Created by Junhyeong Lim on 2017-07-27.
 */
public class ItemWrapper extends Wrapper<ItemStack> {
    public ItemWrapper(ItemStack handle) {
        super(handle);

        // Ensure
        if (getHandle() != null && !getHandle().hasItemMeta()) {
            getHandle().setItemMeta(handle.getItemMeta());
        }
    }

    public static ItemWrapper of(ItemStack item) {
        return new ItemWrapper(item);
    }

    public boolean isEmpty() {
        return ItemUtils.isEmpty(getHandle());
    }

    public boolean isNotEmpty() {
        return !isEmpty();
    }

    @Override
    public ItemWrapper clone() {
        return new ItemWrapper(getHandle().clone());
    }

    public ItemWrapper setLore(String... lores) {
        ItemMeta meta = getHandle().getItemMeta();
        meta.setLore(asColorizeList(lores));
        getHandle().setItemMeta(meta);

        return this;
    }

    public ItemWrapper setLore(Function<String, String> function) {
        ItemMeta meta = getHandle().getItemMeta();
        List<String> lores = meta.getLore();
        if (lores != null) {
            meta.setLore(lores.parallelStream()
                    .map(lore -> MCUtils.colorize(function.apply(lore)))
                    .collect(Collectors.toList()));
        }
        getHandle().setItemMeta(meta);

        return this;
    }

    public ItemWrapper addLore(String... lores) {
        ItemMeta meta = getHandle().getItemMeta();
        List<String> loreList = meta.getLore();
        loreList.addAll(asColorizeList(lores));
        getHandle().setItemMeta(meta);

        return this;
    }

    public List<String> getLore() {
        ItemMeta meta = getHandle().getItemMeta();
        return meta.getLore();
    }

    public ItemWrapper setName(String displayName) {
        ItemMeta meta = getHandle().getItemMeta();
        meta.setDisplayName(displayName);
        getHandle().setItemMeta(meta);

        return this;
    }

    public String getDisplayName(String def) {
        ItemMeta meta = getHandle().getItemMeta();
        String displayName = meta.getDisplayName();
        return displayName != null ? displayName : def;
    }
}
