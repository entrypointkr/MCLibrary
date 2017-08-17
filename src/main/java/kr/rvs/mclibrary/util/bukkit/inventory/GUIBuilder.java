package kr.rvs.mclibrary.util.bukkit.inventory;

import kr.rvs.mclibrary.util.general.VarargsParser;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Junhyeong Lim on 2017-08-17.
 */
public class GUIBuilder {
    private final InventoryType type;
    private int size = 9;
    private String title;
    private final Map<Integer, ItemStack> itemMap = new HashMap<>();
    private final Map<Integer, GUIListener> listenerMap = new HashMap<>();

    public GUIBuilder(InventoryType type) {
        this.type = type;
        this.title = type.getDefaultTitle();
    }

    public GUIBuilder size(int size) {
        Validate.isTrue(size % 9 == 0,
                "Inventory size must be a multiple of 9");
        this.size = size;
        return this;
    }

    public GUIBuilder title(String title) {
        this.title = title;
        return this;
    }

    public GUIBuilder item(Integer index, ItemStack item) {
        itemMap.put(index, item);
        return this;
    }

    public GUIBuilder item(ItemStack item, Integer... indexes) {
        for (Integer index : indexes) {
            item(index, item);
        }
        return this;
    }

    public GUIBuilder item(Object... args) {
        Class[] types = new Class[]{Integer.class, GUIListener.class};
        VarargsParser parser = new VarargsParser(args, types)
                .count(2);
        parser.parse(section ->
                item((Integer) section.get(0), section.get(1)));

        return this;
    }

    public GUIBuilder listener(Integer index, GUIListener listener) {
        listenerMap.put(index, listener);
        return this;
    }

    public GUIBuilder listener(GUIListener listener, Integer... indexes) {
        for (Integer index : indexes) {
            listener(index, listener);
        }
        return this;
    }

    public GUIBuilder listener(Object... args) {
        Class[] types = new Class[]{Integer.class, GUIListener.class};
        VarargsParser parser = new VarargsParser(args, types)
                .count(2);
        parser.parse(section ->
                listener((Integer) section.get(0), section.get(1)));

        return this;
    }

    public GUI build() {
        Inventory inv = type == InventoryType.CHEST ?
                Bukkit.createInventory(null, size, title) :
                Bukkit.createInventory(null, type, title);
        for (int i = 0; i < inv.getSize(); i++) {
            inv.setItem(i, itemMap.get(i));
        }
        GUI ret = new GUI(inv);
        ret.setListener(listenerMap);

        return ret;
    }
}
