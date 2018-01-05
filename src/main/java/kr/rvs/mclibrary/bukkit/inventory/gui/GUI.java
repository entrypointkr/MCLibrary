package kr.rvs.mclibrary.bukkit.inventory.gui;

import kr.rvs.mclibrary.MCLibrary;
import kr.rvs.mclibrary.bukkit.collection.PlayerHashMap;
import kr.rvs.mclibrary.bukkit.inventory.gui.factory.BaseInventoryFactory;
import kr.rvs.mclibrary.bukkit.inventory.gui.factory.DefaultInventoryProcessor;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Junhyeong Lim on 2017-10-06.
 */
public class GUI {
    private static final PlayerHashMap<GUI> guiMap = new PlayerHashMap<>();
    private final GUIHandlers handlers = new GUIHandlers(this);
    private final GUISignature signature;
    private final InventoryFactory factory;
    private boolean init = true;

    public static void init(MCLibrary plugin) {
        Bukkit.getPluginManager().registerEvents(new GUIListener(), plugin);
    }

    public static PlayerHashMap<GUI> getGuiMap() {
        return guiMap;
    }

    public GUI(GUISignature signature, InventoryFactory factory, GUIHandler... handlers) {
        this.factory = factory;
        this.signature = signature;

        getHandlers().add(handlers);
    }

    public GUI(GUISignature signature, GUIHandler... handlers) {
        this(signature, new DefaultInventoryProcessor(new BaseInventoryFactory()), handlers);
    }

    public GUI(GUIHandler... handlers) {
        this(new GUISignature(), handlers);
    }

    public GUIHandlers getHandlers() {
        return handlers;
    }

    public InventoryFactory getFactory() {
        return factory;
    }

    public GUISignature getSignature() {
        return signature;
    }

    public void open(HumanEntity human) {
        if (init) {
            factory.initialize(this);
            init = false;
        }

        Inventory topInv = human.getOpenInventory().getTopInventory();
        Inventory newInv = factory.create(this, human);
        if (signature.isSimilar(topInv)) {
            topInv.setContents(newInv.getContents());
        } else {
            human.openInventory(newInv);
        }
        guiMap.put(human, this);
    }

    static class GUIListener implements Listener {
        @EventHandler
        public void onClick(InventoryClickEvent e) {
            guiMap.getOptional(e.getWhoClicked()).ifPresent(gui -> {
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

                gui.getHandlers().notify(e);
            });
        }

        @EventHandler
        public void onDrag(InventoryDragEvent e) {
            guiMap.getOptional(e.getWhoClicked()).ifPresent(gui -> gui.getHandlers().notify(e));
        }

        @EventHandler
        public void onClose(InventoryCloseEvent e) {
            guiMap.getOptional(e.getPlayer()).ifPresent(gui -> {
                gui.getHandlers().notify(e);
                GUI.getGuiMap().remove(e.getPlayer());
            });
        }
    }
}
