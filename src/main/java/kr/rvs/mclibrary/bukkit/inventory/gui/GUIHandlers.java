package kr.rvs.mclibrary.bukkit.inventory.gui;

import kr.rvs.mclibrary.Static;
import kr.rvs.mclibrary.bukkit.inventory.event.GUIClickEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryEvent;

import java.util.Arrays;
import java.util.LinkedList;
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

    private final List<GUIHandler> handlers = new LinkedList<>();

    public GUIHandlers addFirst(GUIHandler... handlers) {
        this.handlers.addAll(0, Arrays.asList(handlers));
        return this;
    }

    public GUIHandlers addLast(GUIHandler... handlers) {
        this.handlers.addAll(Arrays.asList(handlers));
        return this;
    }

    public void removeHandler(GUIHandler... handlers) {
        this.handlers.removeAll(Arrays.asList(handlers));
    }

    public void clear() {
        this.handlers.clear();
    }

    public void notify(InventoryEvent event) {
        Consumer<GUIHandler> consumer;
        if (event instanceof InventoryClickEvent) {
            GUIClickEvent clickEvent = new GUIClickEvent((InventoryClickEvent) event, gui);
            consumer = handler -> {
                if (!clickEvent.isIgnore())
                    handler.onClick(clickEvent);
            };
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
