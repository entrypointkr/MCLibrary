package kr.rvs.mclibrary.bukkit.inventory.gui;

import kr.rvs.mclibrary.bukkit.collection.EntityNameHashMap;
import kr.rvs.mclibrary.bukkit.inventory.gui.factory.DefaultInventoryFactory;
import kr.rvs.mclibrary.bukkit.inventory.gui.factory.InventoryFactory;
import kr.rvs.mclibrary.bukkit.inventory.gui.processor.InventoryProcessor;
import kr.rvs.mclibrary.bukkit.inventory.gui.processor.NoOpInventoryProcessor;
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
    private InventoryProcessor processor;
    private final GUIHandlers handlers = new GUIHandlers(this);

    public static void init(Plugin plugin) {
        HandlerList.unregisterAll(LISTENER);
        Bukkit.getPluginManager().registerEvents(LISTENER, plugin);
    }

    public static EntityNameHashMap<GUI> getGuiMap() {
        return GUI_MAP;
    }

    public GUI(GUISignature signature, InventoryFactory factory, InventoryProcessor processor, GUIHandler... handlers) {
        this.signature = signature;
        this.factory = factory;
        this.processor = processor;

        if (processor instanceof Initializable)
            ((Initializable) processor).initialize(this);
        this.handlers.addHandler(handlers);
    }

    public GUI(GUISignature signature, InventoryProcessor processor, GUIHandler... handlers) {
        this(signature, new DefaultInventoryFactory(), processor, handlers);
    }

    public GUI(GUISignature signature, GUIHandler... handlers) {
        this(signature, new NoOpInventoryProcessor(), handlers);
    }

    public void open(HumanEntity human) {
        Inventory topInv = human.getOpenInventory().getTopInventory();
        Inventory inv = factory.create(signature, human);

        processor.process(human, signature, inv);
        if (signature.isSimilar(topInv)) {
            topInv.setContents(inv.getContents());
        } else {
            human.openInventory(inv);
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

    public void setProcessor(InventoryProcessor processor) {
        this.processor = processor;
    }
}
