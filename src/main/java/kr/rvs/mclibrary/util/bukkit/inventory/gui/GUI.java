package kr.rvs.mclibrary.util.bukkit.inventory.gui;

import kr.rvs.mclibrary.MCLibrary;
import kr.rvs.mclibrary.util.bukkit.collection.EntityHashMap;
import kr.rvs.mclibrary.util.bukkit.inventory.event.GUIClickEvent;
import kr.rvs.mclibrary.util.bukkit.inventory.factory.DefaultInventoryFactory;
import kr.rvs.mclibrary.util.bukkit.inventory.factory.InventoryFactory;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerQuitEvent;
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
public abstract class GUI implements GUISignature {
    private static final EntityHashMap<GUI> guiMap = new EntityHashMap<>();
    private static final Listener internalListener = new InternalListener();
    private final List<GUIHandler> handlers = new ArrayList<>();
    private InventoryFactory factory;

    public static void init() {
        HandlerList.unregisterAll(internalListener);
        Bukkit.getPluginManager().registerEvents(internalListener, MCLibrary.getPlugin());
    }

    public GUI() {
        Collection<GUIHandler> handlers = handlers();
        if (handlers != null)
            handlers.addAll(this.handlers);
    }

    public static Optional<GUI> getOptional(Entity entity) {
        return guiMap.getOptional(entity);
    }

    public InventoryType type() {
        return InventoryType.CHEST;
    }

    public int size() {
        return lineSize() * 9;
    }

    public int lineSize() {
        return type().getDefaultSize() / 9;
    }

    public String title() {
        return type().getDefaultTitle();
    }

    public InventoryFactory factory() {
        return new DefaultInventoryFactory();
    }

    @Override
    public Collection<GUIHandler> handlers() {
        return null;
    }

    public InventoryFactory getFactory() {
        if (factory == null) {
            factory = factory();
            factory.initialize(this, type(), title(), size(), contents());
        }
        return factory;
    }

    public final void addHandler(GUIHandler handler) {
        handlers.add(handler);
    }

    public final void addHandlers(Collection<GUIHandler> handlers) {
        this.handlers.addAll(handlers);
    }

    public final void removeHandler(GUIHandler handler) {
        handlers.remove(handler);
    }

    public final void open(HumanEntity human) {
        Validate.notNull(getFactory());

        Inventory topInv = human.getOpenInventory().getTopInventory();
        if (getFactory().isSimilar(topInv)) {
            getFactory().getContents().forEach(topInv::setItem);
        } else {
            human.openInventory(getFactory().create(human));
        }
        guiMap.put(human, this);
    }

    public final void notify(InventoryEvent e) {
        Consumer<GUIHandler> consumer;
        if (e instanceof InventoryClickEvent) {
            consumer = handler -> handler.onClick(new GUIClickEvent((InventoryClickEvent) e, this));
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

        @EventHandler
        public void onQuit(PlayerQuitEvent e) {
            guiMap.remove(e.getPlayer());
        }
    }
}
