package kr.rvs.mclibrary.bukkit.inventory.newgui;

import kr.rvs.mclibrary.bukkit.inventory.newgui.handler.GUIHandler;
import org.bukkit.event.inventory.InventoryEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Junhyeong Lim on 2017-12-11.
 */
public class GUIHandlers {
    private final List<GUIHandler<InventoryEvent>> handlers = new ArrayList<>();

    @SafeVarargs
    public final GUIHandlers add(GUIHandler<InventoryEvent>... handlers) {
        this.handlers.addAll(Arrays.asList(handlers));
        return this;
    }

    public void notify(InventoryEvent event) {
        handlers.forEach(handler -> handler.onEvent(event));
    }
}
