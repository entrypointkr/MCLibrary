package kr.rvs.mclibrary.bukkit.event;

import org.bukkit.event.Event;

import java.util.function.Predicate;

/**
 * Created by JunHyeong Lim on 2018-04-29
 */
public abstract class PredicateEventHandler implements EventHandler {
    private final Predicate<Event> predicate;

    public PredicateEventHandler(Predicate<Event> predicate) {
        this.predicate = predicate;
    }

    @Override
    public void handle(Event event) {
        if (predicate.test(event)) {
            doHandle(event);
        } else {
            elseHandle(event);
        }
    }

    public abstract void doHandle(Event event);

    public void elseHandle(Event event) {
        // Empty
    }
}
