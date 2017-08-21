package kr.rvs.mclibrary.util.bukkit.inventory;

import kr.rvs.mclibrary.MCLibrary;
import kr.rvs.mclibrary.util.bukkit.collection.EntityHashMap;
import kr.rvs.mclibrary.util.bukkit.inventory.factory.InventoryFactory;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * Created by Junhyeong Lim on 2017-08-17.
 */
public class GUI {
    private static final EntityHashMap<GUI> guiMap = new EntityHashMap<>();
    private static final Listener internalListener = new InternalListener();
    private final InventoryFactory factory;
    private final List<GUIHandler> handlers = new ArrayList<>();

    public static void init() {
        HandlerList.unregisterAll(internalListener);
        Bukkit.getPluginManager().registerEvents(internalListener, MCLibrary.getPlugin());
    }

    public static Optional<GUI> getOptional(Entity entity) {
        return guiMap.getOptional(entity);
    }

    public static GUI get(Entity entity) {
        return guiMap.get(entity);
    }

    public GUI(InventoryFactory factory) {
        this.factory = factory;
    }

    public void addHandler(GUIHandler handler) {
        handlers.add(handler);
    }

    public void addHandlers(Collection<GUIHandler> handlers) {
        this.handlers.addAll(handlers);
    }

    public void removeHandler(GUIHandler handler) {
        handlers.remove(handler);
    }

    public void open(HumanEntity human) {
        human.openInventory(factory.create());
        guiMap.put(human, this);
    }

    public void notify(InventoryEvent e) {
        Consumer<GUIHandler> consumer;
        if (e instanceof InventoryClickEvent) {
            consumer = handler -> handler.onClick((InventoryClickEvent) e);
        } else if (e instanceof InventoryCloseEvent) {
            consumer = handler -> handler.onClose((InventoryCloseEvent) e);
        } else if (e instanceof InventoryDragEvent) {
            consumer = handler -> handler.onDrag((InventoryDragEvent) e);
        } else {
            consumer = handler -> handler.onUnknown(e);
        }

        handlers.forEach(consumer);
    }

    static class InternalListener implements Listener {
        @EventHandler
        public void onClick(InventoryClickEvent e) {
            getOptional(e.getWhoClicked()).ifPresent(gui -> {
                InventoryAction action = e.getAction();
                Inventory inv = e.getInventory();

                if (action == InventoryAction.COLLECT_TO_CURSOR) {
                    for (ItemStack item : inv) {
                        if (e.getCursor().isSimilar(item)) {
                            e.setCancelled(true);
                            break;
                        }
                    }
                }

                gui.notify(e);
            });
        }

        @EventHandler
        public void onDrag(InventoryDragEvent e) {
            getOptional(e.getWhoClicked()).ifPresent(gui -> {
                gui.notify(e);
            });
        }

        @EventHandler
        public void onClose(InventoryCloseEvent e) {
            getOptional(e.getPlayer()).ifPresent(gui -> {
                gui.notify(e);
                guiMap.remove(e.getPlayer());
            });
        }
    }
}
