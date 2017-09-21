package kr.rvs.mclibrary.bukkit.item;

import kr.rvs.mclibrary.bukkit.MCUtils;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.stream.Collectors;

import static kr.rvs.mclibrary.bukkit.MCUtils.asColorizeList;

/**
 * Created by Junhyeong Lim on 2017-07-27.
 */
public class ItemWrapper extends AtomicReference<ItemStack> {
    public ItemWrapper(ItemStack handle) {
        super(handle);

        // Ensure
        if (get() != null && !get().hasItemMeta()) {
            get().setItemMeta(handle.getItemMeta());
        }
    }

    public static ItemWrapper of(ItemStack item) {
        return new ItemWrapper(item);
    }

    public boolean isEmpty() {
        return ItemUtils.isEmpty(get());
    }

    public boolean isNotEmpty() {
        return !isEmpty();
    }

    public ItemWrapper setLore(String... lores) {
        ItemMeta meta = get().getItemMeta();
        meta.setLore(asColorizeList(lores));
        get().setItemMeta(meta);

        return this;
    }

    public ItemWrapper setLore(Function<String, String> function) {
        ItemMeta meta = get().getItemMeta();
        List<String> lores = meta.getLore();
        if (lores != null) {
            meta.setLore(lores.parallelStream()
                    .map(lore -> MCUtils.colorize(function.apply(lore)))
                    .collect(Collectors.toList()));
        }
        get().setItemMeta(meta);

        return this;
    }

    public ItemWrapper addLore(String... lores) {
        ItemMeta meta = get().getItemMeta();
        List<String> loreList = Optional.ofNullable(meta.getLore()).orElse(new ArrayList<>());
        loreList.addAll(asColorizeList(lores));
        meta.setLore(loreList);
        get().setItemMeta(meta);

        return this;
    }

    public List<String> getLore() {
        ItemMeta meta = get().getItemMeta();
        return meta.getLore();
    }

    public ItemWrapper setName(String displayName) {
        ItemMeta meta = get().getItemMeta();
        meta.setDisplayName(displayName);
        get().setItemMeta(meta);

        return this;
    }

    public String getDisplayName(String def) {
        ItemMeta meta = get().getItemMeta();
        String displayName = meta.getDisplayName();
        return displayName != null ? displayName : def;
    }

    public ItemWrapper replaceString(Object... args) {
        ItemUtils.replaceString(get(), args);
        return this;
    }

    @Override
    public ItemWrapper clone() {
        return new ItemWrapper(get().clone());
    }
}
