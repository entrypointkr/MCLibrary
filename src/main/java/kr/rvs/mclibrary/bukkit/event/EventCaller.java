package kr.rvs.mclibrary.bukkit.event;

import kr.rvs.mclibrary.MCLibrary;
import kr.rvs.mclibrary.bukkit.location.Locations;
import kr.rvs.mclibrary.bukkit.location.Region;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

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

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onMove(PlayerMoveEvent event) {
        Location from = event.getFrom();
        Location to = event.getTo();
        if (from.toVector().equals(to.toVector()))
            return;

        PlayerWalkEvent walkEvent = new PlayerWalkEvent(event);
        Bukkit.getPluginManager().callEvent(walkEvent);
        if (walkEvent.isCancelled()) {
            event.setCancelled(true);
            return;
        }

        Vector blockFrom = Locations.toBlockVector(from);
        Vector blockTo = Locations.toBlockVector(to);
        if (!blockFrom.equals(blockTo)) {
            PlayerBlockWalkEvent blockWalkEvent = new PlayerBlockWalkEvent(event);
            Bukkit.getPluginManager().callEvent(blockWalkEvent);
            if (blockWalkEvent.isCancelled()) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onWalk(PlayerBlockWalkEvent event) {
        Set<Region> toRegions = new HashSet<>();
        Set<Region> fromRegions = new HashSet<>();

        RegionChangeEvent.getRegisteredRegions().forEach(region -> {
            if (region.isIn(event.getTo())) {
                toRegions.add(region);
            }
            if (region.isIn(event.getFrom())) {
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
