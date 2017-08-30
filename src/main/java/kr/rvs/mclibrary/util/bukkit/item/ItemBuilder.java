package kr.rvs.mclibrary.util.bukkit.item;

import kr.rvs.mclibrary.util.general.Storage;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.UnaryOperator;

/**
 * Created by Junhyeong Lim on 2017-07-27.
 */
public class ItemBuilder {
    private final Material material;
    private int amount = 1;
    private short data = 0;

    private List<UnaryOperator<ItemMeta>> metaProcessors = new ArrayList<>();

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

        metaProcessors.add(meta -> item.getItemMeta());
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
        metaProcessors.add((MetaProcessor) meta -> meta.setDisplayName(displayName));
        return this;
    }

    public ItemBuilder lore(String... lores) {
        metaProcessors.add((MetaProcessor) meta -> meta.setLore(Arrays.asList(lores)));
        return this;
    }

    public ItemBuilder lore(List<String> lores) {
        metaProcessors.add((MetaProcessor) meta -> meta.setLore(lores));
        return this;
    }

    public ItemBuilder setSkullOwner(String owner) {
        metaProcessors.add((MetaProcessor) meta -> {
            if (meta instanceof SkullMeta) {
                SkullMeta skullMeta = (SkullMeta) meta;
                skullMeta.setOwner(owner);
            }
        });
        return this;
    }

    public ItemStack build() {
        ItemStack itemStack = new ItemStack(material, amount, data);
        Storage<ItemMeta> storage = new Storage<>(itemStack.getItemMeta());

        metaProcessors.forEach(operator ->
                storage.setValue(operator.apply(storage.getValue())));

        itemStack.setItemMeta(storage.getValue());
        return itemStack;
    }

    public ItemWrapper buildAndWrapping() {
        return new ItemWrapper(build());
    }

    @FunctionalInterface
    interface MetaProcessor extends UnaryOperator<ItemMeta> {
        void process(ItemMeta meta);

        @Override
        default ItemMeta apply(ItemMeta itemMeta) {
            process(itemMeta);
            return itemMeta;
        }
    }
}
