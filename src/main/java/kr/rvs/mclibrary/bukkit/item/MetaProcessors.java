package kr.rvs.mclibrary.bukkit.item;

import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Created by Junhyeong Lim on 2017-09-16.
 */
public class MetaProcessors {
    private final List<Consumer<ItemMeta>> consumers = new ArrayList<>();

    public void add(Consumer<ItemMeta> consumer) {
        this.consumers.add(consumer);
    }

    public void process(ItemMeta meta) {
        consumers.forEach(consumer -> consumer.accept(meta));
    }
}
