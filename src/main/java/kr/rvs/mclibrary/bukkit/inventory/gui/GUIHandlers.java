package kr.rvs.mclibrary.bukkit.inventory.gui;

import kr.rvs.mclibrary.bukkit.inventory.event.GUIClickEvent;
import kr.rvs.mclibrary.bukkit.inventory.gui.handler.DelegateClickHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Junhyeong Lim on 2017-10-06.
 */
public class GUIHandlers {
    private final GUI gui;
    private final List<GUIHandler> handlers = new ArrayList<>();

    public GUIHandlers(GUI gui) {
        this.gui = gui;
    }

    public GUIHandlers addFirst(GUIHandler... handlers) {
        this.handlers.addAll(0, Arrays.asList(handlers));
        return this;
    }

    public GUIHandlers addLast(GUIHandler... handlers) {
        this.handlers.addAll(Arrays.asList(handlers));
        return this;
    }

    public GUIHandlers addFirst(GUIClickHandler handler, Integer... slots) {
        return addFirst(new DelegateClickHandler(handler, slots));
    }

    public GUIHandlers addLast(GUIClickHandler handler, Integer... slots) {
        return addLast(new DelegateClickHandler(handler, slots));
    }

    public void notify(InventoryEvent event) {
        GUIEvent<InventoryEvent> guiEvent = new GUIEvent<>(gui, convert(event));
        for (GUIHandler handler : handlers) {
            handler.handle(guiEvent);

            if (guiEvent.isConsume())
                break;
        }
    }

    private InventoryEvent convert(InventoryEvent event) {
        if (event instanceof InventoryClickEvent) {
            event = new GUIClickEvent((InventoryClickEvent) event, gui);
        }
        return event;
    }
}
