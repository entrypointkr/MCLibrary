package kr.rvs.mclibrary.bukkit.item;

import kr.rvs.mclibrary.bukkit.MCUtils;
import kr.rvs.mclibrary.general.StringUtil;
import kr.rvs.mclibrary.general.VarargsParser;
import org.apache.commons.lang.Validate;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static kr.rvs.mclibrary.bukkit.MCUtils.colorize;

/**
 * Created by Junhyeong Lim on 2017-07-27.
 */
public class ItemBuilder {
    private final Material material;
    private int amount = 1;
    private short data = 0;
    private final MetaProcessors metaProcessors = new MetaProcessors();

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
        this.data = (short) data;
        return this;
    }

    public ItemBuilder display(String displayName) {
        Validate.notNull(displayName);
        metaProcessors.add(meta -> meta.setDisplayName(colorize(displayName)));
        return this;
    }

    public ItemBuilder lore(List<String> lores) {
        Validate.notNull(lores);
        metaProcessors.add(meta ->
                meta.setLore(lores.parallelStream()
                        .map(MCUtils::colorize)
                        .collect(Collectors.toList())));
        return this;
    }

    public ItemBuilder lore(String... lores) {
        Validate.notNull(lores);
        lore(Arrays.asList(lores));
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

    @SuppressWarnings("deprecation")
    public ItemBuilder skullOwner(String owner) {
        Validate.isTrue(material == Material.SKULL_ITEM);

        data = 3;
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
        ItemStack itemStack = new ItemStack(material, amount, data);
        ItemMeta meta = itemStack.getItemMeta();
        metaProcessors.process(meta);
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    public ItemWrapper buildAndWrapping() {
        return new ItemWrapper(build());
    }
}
