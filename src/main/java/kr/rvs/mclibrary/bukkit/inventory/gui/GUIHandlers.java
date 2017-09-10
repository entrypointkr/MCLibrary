package kr.rvs.mclibrary.bukkit.inventory.gui;

import kr.rvs.mclibrary.Static;
import kr.rvs.mclibrary.bukkit.inventory.event.GUIClickEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.logging.Level;

/**
 * Created by Junhyeong Lim on 2017-09-10.
 */
public class GUIHandlers {
    private final GUI gui;

    public GUIHandlers(GUI gui) {
        this.gui = gui;
    }

    private final List<GUIHandler> handlers = new ArrayList<>();

    public void addHandler(GUIHandler... handlers) {
        this.handlers.addAll(Arrays.asList(handlers));
    }

    public void removeHandler(GUIHandler handler) {
        this.handlers.remove(handler);
    }

    public void notify(InventoryEvent event) {
        Consumer<GUIHandler> consumer;
        if (event instanceof InventoryClickEvent) {
            consumer = handler -> handler.onClick(new GUIClickEvent((InventoryClickEvent) event, gui));
        } else if (event instanceof InventoryCloseEvent) {
            consumer = handler -> handler.onClose((InventoryCloseEvent) event);
        } else if (event instanceof InventoryDragEvent) {
            consumer = handler -> handler.onDrag((InventoryDragEvent) event);
        } else {
            Static.log(Level.WARNING, "Unknown event type: " + event.getEventName());
            return;
        }

        handlers.forEach(consumer);
    }
}
