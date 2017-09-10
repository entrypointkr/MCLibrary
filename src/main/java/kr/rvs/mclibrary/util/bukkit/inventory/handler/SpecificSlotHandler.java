package kr.rvs.mclibrary.util.bukkit.inventory.handler;

import kr.rvs.mclibrary.util.bukkit.inventory.InventoryUtils;
import kr.rvs.mclibrary.util.bukkit.inventory.event.GUIClickEvent;
import kr.rvs.mclibrary.util.bukkit.inventory.gui.GUIHandler;
import org.bukkit.event.inventory.InventoryAction;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Junhyeong Lim on 2017-08-20.
 */
public abstract class SpecificSlotHandler implements GUIHandler {
    private final Set<Integer> slots;

    public SpecificSlotHandler(Set<Integer> slots) {
        this.slots = slots;
    }

    public SpecificSlotHandler(Integer... slots) {
        this.slots = new HashSet<>(Arrays.asList(slots));
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
