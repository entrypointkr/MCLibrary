package kr.rvs.mclibrary.util.bukkit.inventory.handler;

import kr.rvs.mclibrary.util.bukkit.inventory.GUIHandler;
import kr.rvs.mclibrary.util.bukkit.inventory.InventoryUtils;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;

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

    public void onClick(InventoryClickEvent e) {
        if (e.getAction() != InventoryAction.NOTHING
                && this.slots.contains(InventoryUtils.movedSlot(e))) {
            this.receive(e);
        }
    }

    public abstract void receive(InventoryClickEvent e);
}
