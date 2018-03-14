package kr.rvs.mclibrary.bukkit.event;

import kr.rvs.mclibrary.MCLibrary;
import kr.rvs.mclibrary.bukkit.location.Region;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Junhyeong Lim on 2017-10-09.
 */
public class EventCaller implements Listener {
    private static final Listener INSTANCE = new EventCaller();

    public static void init(MCLibrary plugin) {
        HandlerList.unregisterAll(INSTANCE);
        Bukkit.getPluginManager().registerEvents(INSTANCE, plugin);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onMove(PlayerMoveEvent event) {
        Location from = event.getFrom();
        Location to = event.getTo();
        if (from.toVector().equals(to.toVector()))
            return;

        PlayerWalkEvent custom = new PlayerWalkEvent(event);
        Bukkit.getPluginManager().callEvent(custom);
        if (event.isCancelled()) {
            event.setCancelled(custom.isCancelled());
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onWalk(PlayerWalkEvent event) {
        Set<Region> toRegions = new HashSet<>();
        Set<Region> fromRegions = new HashSet<>();

        RegionChangeEvent.getRegisteredRegions().forEach(region -> {
            if (region.isIn(event.getTo())) {
                toRegions.add(region);
            } else if (region.isIn(event.getFrom())) {
                fromRegions.add(region);
            }
        });

        if (!toRegions.equals(fromRegions)) {
            RegionChangeEvent regionEvent = new RegionChangeEvent(event.getPlayer(), toRegions, fromRegions);
            Bukkit.getPluginManager().callEvent(regionEvent);
            if (regionEvent.isCancelled()) {
                event.setCancelled(true);
            }
        }
    }
}
