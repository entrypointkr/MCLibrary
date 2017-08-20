package kr.rvs.mclibrary.util.bukkit.item;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

/**
 * Created by Junhyeong Lim on 2017-07-27.
 */
public class ItemBuilder {
    private final Material material;
    private int amount = 1;
    private short data = 0;

    private String displayName = null;
    private String[] lores = null;

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

    public ItemBuilder amount(int amount) {
        this.amount = amount;
        return this;
    }

    public ItemBuilder data(int data) {
        this.data = (short) data;
        return this;
    }

    public ItemBuilder name(String displayName) {
        this.displayName = displayName;
        return this;
    }

    public ItemBuilder lore(String... lores) {
        this.lores = lores;
        return this;
    }

    public ItemStack build() {
        ItemStack itemStack = new ItemStack(material, amount, data);
        ItemMeta meta = itemStack.getItemMeta();

        if (displayName != null)
            meta.setDisplayName(displayName);
        if (lores != null)
            meta.setLore(Arrays.asList(lores));

        itemStack.setItemMeta(meta);

        return itemStack;
    }

    public ItemWrapper buildAndWrapping() {
        return new ItemWrapper(build());
    }
}
