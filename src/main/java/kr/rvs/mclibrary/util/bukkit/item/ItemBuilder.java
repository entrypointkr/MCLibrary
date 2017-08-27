package kr.rvs.mclibrary.util.bukkit.item;

import kr.rvs.mclibrary.util.bukkit.MCUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Junhyeong Lim on 2017-07-27.
 */
public class ItemBuilder {
    private final Material material;
    private int amount = 1;
    private short data = 0;

    private String displayName = null;
    private List<String> lore = null;

    public ItemBuilder(Material material) {
        this.material = material;
    }

    public ItemBuilder(Material material, int amount) {
        this.material = material;
        this.amount = amount;
    }

    public ItemBuilder(Material material, short data) {
        this.material = material;
        this.data = data;
    }

    public ItemBuilder(Material material, int amount, short data) {
        this.material = material;
        this.amount = amount;
        this.data = data;
    }

    public ItemBuilder(ItemStack item) {
        this.material = item.getType();
        this.amount = item.getAmount();
        this.data = item.getDurability();

        ItemWrapper wrapped = new ItemWrapper(item);
        this.displayName = wrapped.getDisplayName(null);
        this.lore = wrapped.getLore();
    }

    public ItemBuilder(ItemWrapper wrapped) {
        this(wrapped.getHandle());
    }

    public ItemBuilder amount(int amount) {
        this.amount = amount;
        return this;
    }

    public ItemBuilder data(int data) {
        this.data = (short) data;
        return this;
    }

    public ItemBuilder display(String displayName) {
        this.displayName = displayName;
        return this;
    }

    public ItemBuilder lore(String... lores) {
        this.lore = Arrays.asList(lores);
        return this;
    }

    public ItemStack build() {
        ItemStack itemStack = new ItemStack(material, amount, data);
        ItemMeta meta = itemStack.getItemMeta();

        if (displayName != null)
            meta.setDisplayName(MCUtils.colorize(displayName));
        if (lore != null)
            meta.setLore(MCUtils.colorize(lore));

        itemStack.setItemMeta(meta);

        return itemStack;
    }

    public ItemWrapper buildAndWrapping() {
        return new ItemWrapper(build());
    }
}
