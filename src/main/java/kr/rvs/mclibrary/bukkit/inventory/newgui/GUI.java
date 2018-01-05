package kr.rvs.mclibrary.bukkit.inventory.newgui;

import kr.rvs.mclibrary.collection.OptionalHashMap;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;

import java.util.Optional;
import java.util.function.Consumer;

/**
 * Created by Junhyeong Lim on 2017-12-11.
 */
public abstract class GUI {
    private static final OptionalHashMap<String, GUI> GUI_MAP = new OptionalHashMap<>();
    private final GUIData data;
    private final GUIHandlers handlers = new GUIHandlers();

    public static Optional<GUI> get(String name) {
        return GUI_MAP.getOptional(name);
    }

    public static Optional<GUI> get(HumanEntity human) {
        return get(human.getName());
    }

    public static Optional<GUI> remove(String name) {
        return GUI_MAP.removeOptional(name);
    }

    public static Optional<GUI> remove(HumanEntity human) {
        return remove(human.getName());
    }

    public static void init(Plugin plugin) {
        Bukkit.getPluginManager().registerEvents(new Listener() {
            @EventHandler
            public void onClick(InventoryClickEvent event) {
                get(event.getWhoClicked()).ifPresent(gui -> gui.onClick(event));
            }

            @EventHandler
            public void onDrag(InventoryDragEvent event) {
                get(event.getWhoClicked()).ifPresent(gui -> gui.onDrag(event));
            }

            @EventHandler
            public void onOpen(InventoryOpenEvent event) {
                get(event.getPlayer()).ifPresent(gui -> gui.onOpen(event));
            }

            @EventHandler
            public void onClose(InventoryCloseEvent event) {
                remove(event.getPlayer()).ifPresent(gui -> gui.onClose(event));
            }
        }, plugin);
    }

    public GUI(GUIData data) {
        this.data = data;
    }

    public GUI handler(Consumer<GUIHandlers> callback) {
        callback.accept(handlers);
        return this;
    }

    public GUIData getData() {
        return data;
    }

    public GUIHandlers getHandlers() {
        return handlers;
    }

    protected abstract void onOpen(InventoryOpenEvent event);

    protected abstract void onClose(InventoryCloseEvent event);

    protected abstract void onClick(InventoryClickEvent event);

    protected abstract void onDrag(InventoryDragEvent event);

    protected abstract Inventory createInventory();

    public void open(HumanEntity human) {
        Inventory topInv = human.getOpenInventory().getTopInventory();
        Inventory newInv = createInventory();
        if (topInv != null && topInv.getTitle().equals(data.title())
                && topInv.getSize() == data.size()) {
            topInv.setContents(newInv.getContents());
            if (human instanceof Player) {
                ((Player) human).updateInventory();
            }
        } else {
            human.openInventory(newInv);
        }
        GUI_MAP.put(human.getName(), this);
    }
}
