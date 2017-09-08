package kr.rvs.mclibrary.util.bukkit.inventory.gui;

import kr.rvs.mclibrary.util.general.VarargsParser;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.HashMap;

/**
 * Created by Junhyeong Lim on 2017-09-07.
 */
public class GUIContents extends HashMap<Integer, ItemStack> {
    public GUIContents item(Integer index, ItemStack item) {
        put(index, item);
        return this;
    }

    public GUIContents item(ItemStack item, Integer... indexes) {
        for (Integer index : indexes) {
            item(index, item);
        }
        return this;
    }

    public GUIContents item(ItemStack... items) {
        for (int i = 0; i < items.length; i++) {
            item(i, items[i]);
        }
        return this;
    }

    /**
     * This method can use two types of multiple args.
     * For example, contents(1, contents, 2, contents, 10, contents)
     *
     * @param args (int slot, ItemStack contents)...
     */
    public GUIContents item(Object... args) {
        VarargsParser parser = new VarargsParser(args);
        parser.parse(section ->
                item(section.<Integer>get(0), section.get(1)));

        return this;
    }

    public GUIContents item(Collection<ItemStack> items) {
        int index = 0;
        for (ItemStack item : items) {
            item(index++, item);
        }
        return this;
    }
}
