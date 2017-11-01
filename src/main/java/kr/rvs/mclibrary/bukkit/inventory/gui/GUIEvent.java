package kr.rvs.mclibrary.bukkit.inventory.gui;

import org.bukkit.event.Cancellable;
import org.bukkit.event.inventory.InventoryEvent;

import java.util.Optional;

/**
 * Created by Junhyeong Lim on 2017-10-06.
 */
public class GUIEvent<E extends InventoryEvent> implements Cancellable { // TODO: Remove
    private final GUI gui;
    private final E event;
    private boolean consume = false;

    public GUIEvent(GUI gui, E event) {
        this.gui = gui;
        this.event = event;
    }

    public GUIEvent(GUIEvent<E> event) {
        this(event.getGui(), event.getEvent());
        this.consume = event.isConsume();
        setCancelled(event.isCancelled());
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

    public GUI getGui() {
        return gui;
    }

    @SuppressWarnings("unchecked")
    public <T extends InventoryEvent> Optional<T> getEvent(Class<T> eventClass) {
        E event = getEvent();
        return eventClass.isInstance(event) ?
                Optional.ofNullable((T) event) :
                Optional.empty();
    }
}
