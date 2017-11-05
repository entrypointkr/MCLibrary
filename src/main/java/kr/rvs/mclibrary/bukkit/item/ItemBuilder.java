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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

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
    private final Processors<ItemStack> itemProcessors = new Processors<>();
    private final Processors<ItemMeta> metaProcessors = new Processors<>();

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
        itemProcessors.add(data -> data.setItemMeta(meta));
    }

    public ItemBuilder(ItemWrapper wrapped) {
        this(wrapped.getHandle());
    }

    private void loreFormatting(List<String> lore) {
        for (int i = 0; i < lore.size(); i++) {
            lore.set(i, MCUtils.colorize("&f" + lore.get(i)));
        }
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
        loreFormatting(lores);
        metaProcessors.add(meta -> {
            List<String> lore = meta.getLore() != null
                    ? meta.getLore() : new ArrayList<>();
            lore.addAll(lores);
            meta.setLore(lore);
        });
        return this;
    }

    public ItemBuilder lore(String... lores) {
        Validate.notNull(lores);
        lore(Arrays.asList(lores));
        return this;
    }

    public ItemBuilder setLore(List<String> lores) {
        metaProcessors.add(meta -> meta.setLore(new ArrayList<>()));
        lore(lores);
        return this;
    }

    public ItemBuilder setLore(String... lores) {
        return setLore(Arrays.asList(lores));
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
        itemProcessors.process(itemStack);
        ItemMeta meta = itemStack.getItemMeta();
        metaProcessors.process(meta);
        itemStack.setItemMeta(meta);
        itemStack.setData(data);
        return itemStack;
    }

    public ItemWrapper buildAndWrapping() {
        return new ItemWrapper(build());
    }

    static class Processors<T> {
        private final List<Consumer<T>> consumers = new ArrayList<>();

        public void add(Consumer<T> consumer) {
            this.consumers.add(consumer);
        }

        public void process(T item) {
            consumers.forEach(consumer -> consumer.accept(item));
        }
    }
}
