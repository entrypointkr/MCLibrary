package kr.rvs.mclibrary.bukkit.event;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 * Created by Junhyeong Lim on 2017-10-09.
 */
public class PlayerWalkEvent extends PlayerMoveEvent {
    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public PlayerWalkEvent(Player player, Location from, Location to) {
        super(player, from, to);
    }

    public PlayerWalkEvent(PlayerMoveEvent event) {
        this(event.getPlayer(), event.getFrom(), event.getTo());
        setCancelled(event.isCancelled());
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
