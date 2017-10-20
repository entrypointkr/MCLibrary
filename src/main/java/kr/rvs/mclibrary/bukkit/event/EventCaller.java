package kr.rvs.mclibrary.bukkit.event;

import kr.rvs.mclibrary.MCLibrary;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 * Created by Junhyeong Lim on 2017-10-09.
 */
public class EventCaller implements Listener {
    public static void init(MCLibrary plugin) {
        Bukkit.getPluginManager().registerEvents(new EventCaller(), plugin);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onMove(PlayerMoveEvent event) {
        Location from = event.getFrom();
        Location to = event.getTo();
        if (from.toVector().equals(to.toVector()))
            return;

        PlayerWalkEvent custom = new PlayerWalkEvent(event);
        Bukkit.getPluginManager().callEvent(custom);
        if (event.isCancelled() != custom.isCancelled()) {
            event.setCancelled(custom.isCancelled());
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInteract(PlayerInteractEvent event) {
        Bukkit.getPluginManager().callEvent(new SafePlayerInteractEvent(event));
    }
}
