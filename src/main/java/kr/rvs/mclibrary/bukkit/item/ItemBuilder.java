package kr.rvs.mclibrary.bukkit.item;

import kr.rvs.mclibrary.bukkit.MCUtils;
import kr.rvs.mclibrary.general.StringUtil;
import kr.rvs.mclibrary.general.VarargsParser;
import org.apache.commons.lang.Validate;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.MaterialData;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static kr.rvs.mclibrary.bukkit.MCUtils.colorize;

/**
 * Created by Junhyeong Lim on 2017-07-27.
 */
@SuppressWarnings("deprecation")
public class ItemBuilder {
    private final Material material;
    private int amount;
    private short durability;
    private MaterialData data;
    private final MetaProcessors metaProcessors = new MetaProcessors();

    public ItemBuilder(Material material, int amount, short durability, MaterialData data) {
        this.material = material;
        this.amount = amount;
        this.durability = durability;
        this.data = data;
    }

    public ItemBuilder(Material material, int durability, MaterialData data) {
        this(material, 1, (short) durability, data);
    }

    public ItemBuilder(Material material, int amount) {
        this(material, amount, (short) 0, new MaterialData(material));
    }

    public ItemBuilder(Material material) {
        this(material, 1);
    }

    public ItemBuilder(ItemStack item) {
        this(item.getType(), item.getAmount(), item.getDurability(), item.getData());

        ItemMeta meta = item.getItemMeta();
        String displayName = meta.getDisplayName();
        List<String> lore = meta.getLore();

        if (displayName != null)
            display(displayName);
        if (lore != null)
            lore(meta.getLore());
    }

    public ItemBuilder(ItemWrapper wrapped) {
        this(wrapped.getHandle());
    }

    public ItemBuilder amount(int amount) {
        this.amount = amount;
        return this;
    }

    public ItemBuilder data(int data) {
        this.durability = (short) data;
        return this;
    }

    public ItemBuilder data(MaterialData data) {
        this.data = data;
        return this;
    }

    public ItemBuilder display(String displayName) {
        Validate.notNull(displayName);
        metaProcessors.add(meta -> meta.setDisplayName(colorize(displayName)));
        return this;
    }

    public ItemBuilder lore(List<String> lores) {
        Validate.notNull(lores);
        metaProcessors.add(meta -> {
            for (int i = 0; i < lores.size(); i++) {
                lores.set(i, MCUtils.colorize("&f" + lores.get(i)));
            }
            meta.setLore(lores);
        });
        return this;
    }

    public ItemBuilder lore(String... lores) {
        Validate.notNull(lores);
        lore(Arrays.asList(lores));
        return this;
    }

    public ItemBuilder enchant(Object... args) {
        metaProcessors.add(meta ->
                new VarargsParser(args).parse(section ->
                        meta.addEnchant(
                                section.<Enchantment>get(0),
                                section.<Integer>get(1),
                                false
                        )));
        return this;
    }

    public ItemBuilder loreWithLineBreak(int count, Collection<String> lores) {
        metaProcessors.add(meta -> {
            StringBuilder serialized = new StringBuilder(lores.size());
            lores.forEach(serialized::append);
            meta.setLore(StringUtil.lineBreak(serialized, count));
        });
        return this;
    }

    public ItemBuilder loreWithLineBreak(int count, String... lores) {
        return loreWithLineBreak(count, Arrays.asList(lores));
    }

    public ItemBuilder loreWithLineBreak(Collection<String> lores) {
        return loreWithLineBreak(15, lores);
    }

    public ItemBuilder loreWithLineBreak(String... lores) {
        return loreWithLineBreak(Arrays.asList(lores));
    }

    public ItemBuilder skullOwner(String owner) {
        Validate.isTrue(material == Material.SKULL_ITEM);

        durability = 3;
        metaProcessors.add(meta -> {
            if (meta instanceof SkullMeta) {
                SkullMeta skullMeta = (SkullMeta) meta;
                skullMeta.setOwner(owner);
            }
        });
        return this;
    }

    public ItemBuilder skullOwner(HumanEntity human) {
        return skullOwner(human.getName());
    }

    public ItemBuilder replacement(Object... args) {
        metaProcessors.add(meta ->
                new VarargsParser(args).parse(section ->
                        ItemUtils.replaceString(meta, section.getString(0), section.getString(1))));
        return this;
    }

    public ItemStack build() {
        ItemStack itemStack = new ItemStack(material, amount, durability);
        ItemMeta meta = itemStack.getItemMeta();
        metaProcessors.process(meta);
        itemStack.setItemMeta(meta);
        itemStack.setData(data);
        return itemStack;
    }

    public ItemWrapper buildAndWrapping() {
        return new ItemWrapper(build());
    }
}
