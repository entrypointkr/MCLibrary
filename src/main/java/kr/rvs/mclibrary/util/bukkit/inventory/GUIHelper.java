package kr.rvs.mclibrary.util.bukkit.inventory;

import kr.rvs.mclibrary.MCLibrary;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * Created by Junhyeong Lim on 2017-07-26.
 */

public class GUIHelper {
    private static final org.bukkit.event.Listener INTERNAL_LISTENER = new InternalListener();
    private static final Map<UUID, GUIHelper> HELPER_MAP = new HashMap<>();

    static {
        Bukkit.getPluginManager().registerEvents(
                INTERNAL_LISTENER, MCLibrary.self);
    }

    private final InventoryType type;
    private Set<Listener> listeners = new HashSet<>();
    private Map<Integer, ItemStack> itemMap = new HashMap<>();
    private String title;
    private int size = 9;

    public GUIHelper(InventoryType type) {
        this.type = type;
        this.title = type.getDefaultTitle();
    }

    private static void close(Entity entity) {
        Validate.notNull(entity);

        HELPER_MAP.remove(entity.getUniqueId());
    }

    private static GUIHelper getHelper(Entity entity) {
        Validate.notNull(entity);

        return HELPER_MAP.get(entity.getUniqueId());
    }

    private static void putEntity(Entity entity, GUIHelper helper) {
        Validate.notNull(entity);

        HELPER_MAP.put(entity.getUniqueId(), helper);
    }

    public static boolean isIn(Entity entity) {
        Validate.notNull(entity);

        return HELPER_MAP.containsKey(entity.getUniqueId());
    }

    public GUIHelper setTitle(String title) {
        Validate.notNull(title);

        this.title = title;
        return this;
    }

    public GUIHelper setSize(int size) {
        Validate.isTrue(size % 9 == 0);

        this.size = size;
        return this;
    }

    public Inventory create() {
        Inventory inv;
        if (this.type == InventoryType.CHEST) {
            inv = Bukkit.createInventory(null, size, title);
        } else {
            inv = Bukkit.createInventory(null, type, title);
        }

        setItemToInventory(inv);
        return inv;
    }

    private void setItemToInventory(Inventory inv) {
        Validate.notNull(inv);

        for (Map.Entry<Integer, ItemStack> entry : itemMap.entrySet()) {
            inv.setItem(entry.getKey(), entry.getValue());
        }
    }

    public GUIHelper open(Player player) {
        Validate.notNull(player);

        player.openInventory(create());

        return this;
    }

    public GUIHelper putListener(Listener listener) {
        Validate.notNull(listener);

        listeners.add(listener);
        return this;
    }

    public GUIHelper putItem(Integer index, ItemStack item) {
        Validate.notNull(index);
        Validate.notNull(item);

        this.itemMap.put(index, item);
        return this;
    }

    private void notifyListeners(InventoryClickEvent e) {
        Validate.notNull(e);

        for (Listener listener : listeners) {
            listener.onClick(e);
        }
    }

    public interface Listener {
        void onClick(InventoryClickEvent e);
    }

    private static class InternalListener implements org.bukkit.event.Listener {
        @EventHandler
        public void onClick(InventoryClickEvent e) {
            HumanEntity clickedHuman = e.getWhoClicked();
            GUIHelper helper = getHelper(clickedHuman);
            InventoryAction action = e.getAction();
            Inventory inv = e.getInventory();
            int rawSlot = e.getRawSlot();

            if (helper == null)
                return;

            if (action == InventoryAction.MOVE_TO_OTHER_INVENTORY) {
                rawSlot = inv.firstEmpty();
            } else if (action == InventoryAction.COLLECT_TO_CURSOR) {
                for (int i = 0; i < inv.getSize(); i++) {
                    ItemStack item = inv.getItem(i);
                    if (e.getCursor().isSimilar(item)) {
                        e.setCancelled(true);
                        break;
                    }
                }
            }

            if (rawSlot > inv.getSize())
                return;

            helper.notifyListeners(e);
        }

        @EventHandler
        public void onClose(InventoryCloseEvent e) {
            close(e.getPlayer());
        }

        @EventHandler
        public void onQuit(PlayerQuitEvent e) {
            close(e.getPlayer());
        }
    }
}
