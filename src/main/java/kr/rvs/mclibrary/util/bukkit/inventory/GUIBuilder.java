package kr.rvs.mclibrary.util.bukkit.inventory;

import kr.rvs.mclibrary.util.bukkit.inventory.factory.DefaultInventoryFactory;
import kr.rvs.mclibrary.util.bukkit.inventory.factory.InventoryFactory;
import kr.rvs.mclibrary.util.bukkit.inventory.factory.SingleInventoryFactory;
import kr.rvs.mclibrary.util.bukkit.inventory.handler.EventCancelHandler;
import kr.rvs.mclibrary.util.general.VarargsParser;
import org.apache.commons.lang.Validate;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

import java.util.*;

import static kr.rvs.mclibrary.util.bukkit.MCUtils.colorize;

/**
 * Created by Junhyeong Lim on 2017-08-17.
 */
public class GUIBuilder {
    private final InventoryType type;
    private int size;
    private String title;
    private InventoryFactory factory;

    private final Map<Integer, ItemStack> itemMap = new HashMap<>();
    private final List<GUIHandler> handlers = new ArrayList<>();

    public GUIBuilder(InventoryType type) {
        this.type = type;
        this.size = type.getDefaultSize();
        this.title = type.getDefaultTitle();

        factory(new DefaultInventoryFactory());
    }

    public GUIBuilder size(int size) {
        Validate.isTrue(size % 9 == 0,
                "Inventory size must be a multiple of 9");
        this.size = size;
        return this;
    }

    public GUIBuilder lineSize(int line) {
        Validate.isTrue(line <= 6);
        this.size = line * 9;
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

    public GUIBuilder item(ItemStack... items) {
        for (int i = 0; i < items.length; i++) {
            item(i, items[i]);
        }
        return this;
    }

    /**
     * This method can use two types of multiple args.
     * For example, item(1, item, 2, item, 10, item)
     *
     * @param args (int slot, ItemStack item)...
     */
    public GUIBuilder item(Object... args) {
        VarargsParser parser = new VarargsParser(args);
        parser.parse(section ->
                item(section.<Integer>get(0), section.get(1)));

        return this;
    }

    public GUIBuilder handler(GUIHandler... handlers) {
        this.handlers.addAll(Arrays.asList(handlers));
        return this;
    }

    public GUIBuilder factory(InventoryFactory factory) {
        this.factory = factory;
        return this;
    }

    public GUI build() {
        factory.initialize(type, colorize(title), size, itemMap);
        GUI ret = new GUI(factory);
        ret.addHandlers(handlers);

        return ret;
    }

    public static void main(String[] args) {
        // Example
        GUI gui = new GUIBuilder(InventoryType.CHEST)
                .size(54)
                .title("test")
                .item(
                        0, new ItemStack(Material.STONE),
                        1, new ItemStack(Material.APPLE),
                        4, new ItemStack(Material.BEDROCK)
                )
                .handler(new EventCancelHandler())
                .factory(new SingleInventoryFactory(new DefaultInventoryFactory()))
                .build();

        Player player = null;
        gui.open(player);
    }
}
