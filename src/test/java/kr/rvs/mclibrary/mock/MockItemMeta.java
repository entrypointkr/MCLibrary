package kr.rvs.mclibrary.mock;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Junhyeong Lim on 2017-07-27.
 */
public class MockItemMeta implements ItemMeta {
    String displayName = "";
    List<String> lore = new ArrayList<>();

    @Override
    public boolean hasDisplayName() {
        return displayName != null;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    @Override
    public void setDisplayName(String name) {
        this.displayName = name;
    }

    @Override
    public boolean hasLocalizedName() {
        return false;
    }

    @Override
    public String getLocalizedName() {
        return null;
    }

    @Override
    public void setLocalizedName(String name) {

    }

    @Override
    public boolean hasLore() {
        return false;
    }

    @Override
    public List<String> getLore() {
        return lore;
    }

    @Override
    public void setLore(List<String> lore) {
        this.lore = lore;
    }

    @Override
    public boolean hasEnchants() {
        return false;
    }

    @Override
    public boolean hasEnchant(Enchantment ench) {
        return false;
    }

    @Override
    public int getEnchantLevel(Enchantment ench) {
        return 0;
    }

    @Override
    public Map<Enchantment, Integer> getEnchants() {
        return null;
    }

    @Override
    public boolean addEnchant(Enchantment ench, int level, boolean ignoreLevelRestriction) {
        return false;
    }

    @Override
    public boolean removeEnchant(Enchantment ench) {
        return false;
    }

    @Override
    public boolean hasConflictingEnchant(Enchantment ench) {
        return false;
    }

    @Override
    public void addItemFlags(ItemFlag... itemFlags) {

    }

    @Override
    public void removeItemFlags(ItemFlag... itemFlags) {

    }

    @Override
    public Set<ItemFlag> getItemFlags() {
        return null;
    }

    @Override
    public boolean hasItemFlag(ItemFlag flag) {
        return false;
    }

    @Override
    public boolean isUnbreakable() {
        return false;
    }

    @Override
    public void setUnbreakable(boolean unbreakable) {

    }

    @Override
    public ItemMeta clone() {
        return this;
    }

    @Override
    public Spigot spigot() {
        return null;
    }

    @Override
    public Map<String, Object> serialize() {
        return null;
    }
}
