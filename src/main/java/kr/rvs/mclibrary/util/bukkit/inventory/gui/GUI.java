package kr.rvs.mclibrary.util.bukkit.inventory.gui;

import kr.rvs.mclibrary.util.bukkit.collection.EntityNameHashMap;
import kr.rvs.mclibrary.util.bukkit.inventory.gui.factory.DefaultInventoryFactory;
import kr.rvs.mclibrary.util.bukkit.inventory.gui.factory.InventoryFactory;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;

/**
 * Created by Junhyeong Lim on 2017-09-09.
 */
public class GUI {
    private static final Listener LISTENER = new GUIListener();
    private static final EntityNameHashMap<GUI> GUI_MAP = new EntityNameHashMap<>();
    private final GUISignature signature;
    private final InventoryFactory factory;
    private final GUIHandlers handlers = new GUIHandlers();

    public static void init(Plugin plugin) {
        HandlerList.unregisterAll(LISTENER);
        Bukkit.getPluginManager().registerEvents(LISTENER, plugin);
    }

    public static EntityNameHashMap<GUI> getGuiMap() {
        return GUI_MAP;
    }

    public GUI(GUISignature signature, InventoryFactory factory, GUIHandler... handlers) {
        this.signature = signature;
        this.factory = factory;

        this.factory.initialize(this);
        this.handlers.addHandler(handlers);
    }

    public GUI(GUISignature signature, GUIHandler... handlers) {
        this(signature, new DefaultInventoryFactory(), handlers);
    }

    public void open(HumanEntity human) {
        Inventory topInv = human.getOpenInventory().getTopInventory();
        Inventory inv = factory.create(signature, human);
        if (signature.isSimilar(topInv)) {
            topInv.setContents(inv.getContents());
        } else {
            human.openInventory(inv);
            GUI_MAP.put(human, this);
        }
    }

    public GUIHandlers getHandlers() {
        return handlers;
    }
}
