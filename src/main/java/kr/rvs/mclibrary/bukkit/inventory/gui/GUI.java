package kr.rvs.mclibrary.bukkit.inventory.gui;

import kr.rvs.mclibrary.bukkit.collection.EntityNameHashMap;
import kr.rvs.mclibrary.bukkit.inventory.gui.factory.DefaultInventoryFactory;
import kr.rvs.mclibrary.bukkit.inventory.gui.factory.InventoryFactory;
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
    private InventoryFactory factory;
    private final GUIHandlers handlers = new GUIHandlers(this);

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

        if (factory instanceof Initializable)
            ((Initializable) factory).initialize(this);
        this.handlers.addHandler(handlers);
    }

    public GUI(GUISignature signature, GUIHandler... handlers) {
        this(signature, new DefaultInventoryFactory(), handlers);
    }

    public void open(HumanEntity human) {
        Inventory topInv = human.getOpenInventory().getTopInventory();
        Inventory newInv = factory.create(signature, human);

        if (signature.isSimilar(topInv)) {
            topInv.setContents(newInv.getContents());
        } else {
            human.openInventory(newInv);
            GUI_MAP.put(human, this);
        }
    }

    public GUISignature getSignature() {
        return signature;
    }

    public GUIHandlers getHandlers() {
        return handlers;
    }

    public void setFactory(InventoryFactory factory) {
        this.factory = factory;
    }
}
