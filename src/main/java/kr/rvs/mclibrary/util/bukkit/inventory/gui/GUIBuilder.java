package kr.rvs.mclibrary.util.bukkit.inventory.gui;

import kr.rvs.mclibrary.util.bukkit.inventory.factory.CachedInventoryFactory;
import kr.rvs.mclibrary.util.bukkit.inventory.factory.DefaultInventoryFactory;
import kr.rvs.mclibrary.util.bukkit.inventory.factory.InventoryFactory;
import kr.rvs.mclibrary.util.bukkit.inventory.handler.EventCancelHandler;
import org.apache.commons.lang.Validate;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Junhyeong Lim on 2017-08-17.
 */
public class GUIBuilder {
    private final InventoryType type;
    private int size;
    private String title;
    private InventoryFactory factory;
    private GUIContents contents;

    private final List<GUIHandler> handlers = new ArrayList<>();

    public GUIBuilder(InventoryType type) {
        this.type = type;
        this.size = type.getDefaultSize();
        this.title = type.getDefaultTitle();
        this.contents = new GUIContents();

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

    public GUIBuilder contents(GUIContents contents) {
        this.contents = contents;
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
        return new GUIAdapter(type, title, size, contents, handlers, factory);
    }

    public static void main(String[] args) {
        // Example
        GUI gui = new GUIBuilder(InventoryType.CHEST)
                .size(54)
                .title("test")
                .contents(new GUIContents().item(
                        0, new ItemStack(Material.STONE),
                        1, new ItemStack(Material.APPLE),
                        4, new ItemStack(Material.BEDROCK)
                ))
                .handler(new EventCancelHandler())
                .factory(new CachedInventoryFactory(new DefaultInventoryFactory()))
                .build();

        Player player = null;
        gui.open(player);
    }
}
