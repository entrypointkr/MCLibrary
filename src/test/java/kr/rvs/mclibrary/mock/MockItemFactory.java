package kr.rvs.mclibrary.mock;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFactory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Created by Junhyeong Lim on 2017-07-27.
 */
public class MockItemFactory implements ItemFactory {
    @Override
    public ItemMeta getItemMeta(Material material) {
        return new MockItemMeta();
    }

    @Override
    public boolean isApplicable(ItemMeta meta, ItemStack stack) throws IllegalArgumentException {
        return false;
    }

    @Override
    public boolean isApplicable(ItemMeta meta, Material material) throws IllegalArgumentException {
        return true;
    }

    @Override
    public boolean equals(ItemMeta meta1, ItemMeta meta2) throws IllegalArgumentException {
        return meta1 != null && meta2 != null && meta1.equals(meta2);
    }

    @Override
    public ItemMeta asMetaFor(ItemMeta meta, ItemStack stack) throws IllegalArgumentException {
        return null;
    }

    @Override
    public ItemMeta asMetaFor(ItemMeta meta, Material material) throws IllegalArgumentException {
        return meta;
    }

    @Override
    public Color getDefaultLeatherColor() {
        return null;
    }
}
