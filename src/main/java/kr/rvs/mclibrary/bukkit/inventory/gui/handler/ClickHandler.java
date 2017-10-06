package kr.rvs.mclibrary.bukkit.inventory.gui.handler;

import kr.rvs.mclibrary.bukkit.inventory.event.GUIClickEvent;
import kr.rvs.mclibrary.bukkit.inventory.gui.GUIEvent;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Junhyeong Lim on 2017-10-06.
 */
public abstract class ClickHandler extends TopInventoryHandler {
    private final Set<Integer> slots = new HashSet<>();

    public ClickHandler(Integer... slots) {
        this.slots.addAll(Arrays.asList(slots));
    }

    public ClickHandler(Collection<Integer> slots) {
        this.slots.addAll(slots);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void receive(GUIEvent event) {
        event.getEvent(GUIClickEvent.class).ifPresent(e -> {
            InventoryClickEvent clickEvent = (InventoryClickEvent) e;
            if (clickEvent.getAction() != InventoryAction.NOTHING
                    && this.slots.contains(clickEvent.getRawSlot())) {
                this.click(event);
            }
        });
    }

    public abstract void click(GUIEvent<GUIClickEvent> event);
}
