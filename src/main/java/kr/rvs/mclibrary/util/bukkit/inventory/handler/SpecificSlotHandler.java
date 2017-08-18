package kr.rvs.mclibrary.util.bukkit.inventory.handler;

import kr.rvs.mclibrary.util.bukkit.inventory.GUIHandler;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Junhyeong Lim on 2017-08-18.
 */
public abstract class SpecificSlotHandler extends GUIHandler {
    private final Set<Integer> slots;

    public SpecificSlotHandler(Set<Integer> slots) {
        this.slots = slots;
    }

    public SpecificSlotHandler(Integer... slots) {
        this.slots = new HashSet<>(Arrays.asList(slots));
    }

    @Override
    public void onClick(InventoryClickEvent e) {
        if (slots.contains(correctSlot(e))) {
            receive(e);
        }
    }

    public abstract void receive(InventoryClickEvent e);
}
