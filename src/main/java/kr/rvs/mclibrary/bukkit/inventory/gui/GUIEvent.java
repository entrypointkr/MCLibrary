package kr.rvs.mclibrary.bukkit.inventory.gui;

import org.bukkit.event.Cancellable;
import org.bukkit.event.inventory.InventoryEvent;

import java.util.Optional;

/**
 * Created by Junhyeong Lim on 2017-10-06.
 */
public class GUIEvent<E extends InventoryEvent> implements Cancellable {
    private final E event;
    private boolean consume = false;

    public GUIEvent(E event) {
        this.event = event;
    }

    @Override
    public boolean isCancelled() {
        return event instanceof Cancellable && ((Cancellable) event).isCancelled();
    }

    @Override
    public void setCancelled(boolean cancel) {
        if (event instanceof Cancellable)
            ((Cancellable) event).setCancelled(cancel);
    }

    public boolean isConsume() {
        return consume;
    }

    public void setConsume(boolean consume) {
        this.consume = consume;
    }

    public E getEvent() {
        return event;
    }

    @SuppressWarnings("unchecked")
    public <T extends InventoryEvent> Optional<T> getEvent(Class<T> eventClass) {
        E event = getEvent();
        return eventClass.isAssignableFrom(event.getClass()) ?
                Optional.ofNullable((T) event) :
                Optional.empty();
    }
}
