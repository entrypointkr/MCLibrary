package kr.rvs.mclibrary.bukkit.event;

import kr.rvs.mclibrary.MCLibrary;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredListener;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;

/**
 * Created by JunHyeong Lim on 2018-04-28
 */
public class EventHandlerManager extends RegisteredListener {
    private static final EventHandlerManager MANAGER = new EventHandlerManager();
    private final Set<EventHandler> handlers = new HashSet<>();

    static {
        try {
            Field field = HandlerList.class.getDeclaredField("allLists");
            field.setAccessible(true);
            ArrayList<HandlerList> lists = (ArrayList<HandlerList>) field.get(null);
            field.set(null, new HandlerArrayList(lists));
        } catch (Exception ex) {
            throw new IllegalStateException(ex);
        }
    }

    public static EventHandlerManager get() {
        return MANAGER;
    }

    private EventHandlerManager() {
        super(new Listener() {
        }, null, EventPriority.NORMAL, MCLibrary.getPlugin(), false);
    }

    public void register(EventHandler... handlers) {
        this.handlers.addAll(Arrays.asList(handlers));
    }

    public void unregister(EventHandler... handlers) {
        this.handlers.removeAll(Arrays.asList(handlers));
    }

    private void notify(EventHandler handler, Event event) {
        try {
            handler.handle(event);
        } catch (Exception ex) {
            Bukkit.getLogger().log(Level.WARNING, ex, () -> handler.getClass().getSimpleName());
        }
    }

    @Override
    public void callEvent(Event event) {
        for (EventHandler handler : handlers) {
            notify(handler, event);
        }
    }

    static class HandlerArrayList extends ArrayList<HandlerList> {
        HandlerArrayList(Collection<? extends HandlerList> c) {
            super(c);
            c.forEach(handlers -> handlers.register(MANAGER));
        }

        @Override
        public boolean add(HandlerList handlerList) {
            handlerList.register(MANAGER);
            return super.add(handlerList);
        }
    }
}
