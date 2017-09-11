package kr.rvs.mclibrary.bukkit.inventory.handler;

import kr.rvs.mclibrary.bukkit.inventory.InventoryUtils;
import kr.rvs.mclibrary.bukkit.inventory.event.GUIClickEvent;
import kr.rvs.mclibrary.bukkit.inventory.gui.GUIHandler;
import org.bukkit.event.inventory.InventoryAction;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Junhyeong Lim on 2017-08-20.
 */
public abstract class SpecificSlotHandler implements GUIHandler {
    private final Set<Integer> slots = new HashSet<>();

    public SpecificSlotHandler(Integer... slots) {
        this(Arrays.asList(slots));
    }

    public SpecificSlotHandler(Collection<Integer> slots) {
        this.slots.addAll(slots);
    }

    @Override
    public void onClick(GUIClickEvent e) {
        if (e.getAction() != InventoryAction.NOTHING
                && this.slots.contains(InventoryUtils.movedSlot(e))) {
            this.receive(e);
        }
    }

    public abstract void receive(GUIClickEvent e);
}
