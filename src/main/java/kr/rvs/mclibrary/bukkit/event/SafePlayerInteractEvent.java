package kr.rvs.mclibrary.bukkit.event;

import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Created by Junhyeong Lim on 2017-10-21.
 */
public class SafePlayerInteractEvent extends CancellableDelegateEvent<PlayerInteractEvent> {
    private static boolean getHandMethodFound = false;
    private static final HandlerList handlers = new HandlerList();

    static {
        try {
            PlayerInteractEvent.class.getMethod("getHand");
            getHandMethodFound = true;
        } catch (NoSuchMethodException e) {
            // Ignore
        }
    }

    public SafePlayerInteractEvent(PlayerInteractEvent delegate) {
        super(delegate);
    }

    public boolean hasGetHandMethod() {
        return getHandMethodFound;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
