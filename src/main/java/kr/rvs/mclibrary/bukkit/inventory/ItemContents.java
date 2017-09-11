package kr.rvs.mclibrary.bukkit.inventory;

import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Junhyeong Lim on 2017-09-11.
 */
public class ItemContents extends HashMap<Integer, ItemStack> {
    public ItemContents(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    public ItemContents(int initialCapacity) {
        super(initialCapacity);
    }

    public ItemContents() {
    }

    public ItemContents(Map<? extends Integer, ? extends ItemStack> m) {
        super(m);
    }

    public int lastKey() {
        int ret = 0;
        for (int key : keySet()) {
            if (ret < key)
                ret = key;
        }
        return ret;
    }


}
