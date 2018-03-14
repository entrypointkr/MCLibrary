package kr.rvs.mclibrary.bukkit.event;

import kr.rvs.mclibrary.bukkit.location.Region;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by JunHyeong Lim on 2018-03-13
 */
public class RegionChangeEvent extends PlayerEvent implements Cancellable {
    private static final HandlerList HANDLERS = new HandlerList();
    private static final Set<Region> REGIONS = new HashSet<>();
    private final Set<Region> toRegions;
    private final Set<Region> fromRegions;
    private boolean cancel = false;

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public static void register(Region... regions) {
        REGIONS.addAll(Arrays.asList(regions));
    }

    public static void remove(Region... regions) {
        REGIONS.removeAll(Arrays.asList(regions));
    }

    public static Set<Region> getRegisteredRegions() {
        return new HashSet<>(REGIONS);
    }

    public RegionChangeEvent(Player who, Set<Region> toRegions, Set<Region> fromRegions) {
        super(who);
        this.toRegions = Collections.unmodifiableSet(toRegions);
        this.fromRegions = Collections.unmodifiableSet(fromRegions);
    }

    public Set<Region> getToRegions() {
        return toRegions;
    }

    public Set<Region> getFromRegions() {
        return fromRegions;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    @Override
    public boolean isCancelled() {
        return cancel;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancel = cancel;
    }
}
