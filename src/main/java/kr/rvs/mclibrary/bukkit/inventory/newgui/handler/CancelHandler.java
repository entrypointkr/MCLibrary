package kr.rvs.mclibrary.bukkit.inventory.newgui.handler;

import org.bukkit.event.Cancellable;
import org.bukkit.event.inventory.InventoryEvent;

/**
 * Created by Junhyeong Lim on 2017-12-11.
 */
public class CancelHandler implements GUIHandler<InventoryEvent> {
    public static final CancelHandler DEFAULT = new CancelHandler();
    public static final TopHandler TOP = new TopHandler().addHandler(DEFAULT);

    private CancelHandler() {
    }

    @Override
    public void onEvent(InventoryEvent event) {
        if (event instanceof Cancellable) {
            ((Cancellable) event).setCancelled(true);
        }
    }
}
