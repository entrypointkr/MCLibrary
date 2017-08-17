package kr.rvs.mclibrary.util.bukkit.inventory;

import kr.rvs.mclibrary.MCLibrary;
import kr.rvs.mclibrary.util.bukkit.collection.EntityHashMap;
import kr.rvs.mclibrary.util.collection.OptionalHashMap;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Map;
import java.util.Optional;

/**
 * Created by Junhyeong Lim on 2017-08-17.
 */
public class GUI {
    private static final EntityHashMap<GUI> guiMap = new EntityHashMap<>();
    private static final Listener internalListener = new Listener() {
        @EventHandler
        public void onClick(InventoryClickEvent e) {
            getOptional(e.getWhoClicked()).ifPresent(gui -> {
                InventoryAction action = e.getAction();
                Inventory inv = e.getInventory();
                int rawSlot = e.getRawSlot();

                switch (action) {
                    case MOVE_TO_OTHER_INVENTORY:
                        rawSlot = inv.firstEmpty();
                        break;
                    case COLLECT_TO_CURSOR:
                        for (ItemStack item : inv) {
                            if (e.getCursor().isSimilar(item)) {
                                e.setCancelled(true);
                                break;
                            }
                        }
                        break;
                }

                if (rawSlot <= inv.getSize()) {
                    gui.notify(e);
                }
            });
        }
    };
    private final Inventory inv;
    private final OptionalHashMap<Integer, GUIListener> listenerMap = new OptionalHashMap<>();

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

    public GUI(Inventory inv) {
        this.inv = inv;
    }

    public void setListener(Integer index, GUIListener listener) {
        listenerMap.put(index, listener);
    }

    public void setListener(Map<Integer, GUIListener> listenerMap) {
        this.listenerMap.putAll(listenerMap);
    }

    public void open(HumanEntity human) {
        guiMap.put(human, this);
        human.openInventory(inv);
    }

    public void notify(InventoryClickEvent e) {
        listenerMap.getOptional(e.getRawSlot()).ifPresent(listener ->
                listener.onClick(e));
    }
}
