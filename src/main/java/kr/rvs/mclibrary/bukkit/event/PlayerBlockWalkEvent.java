package kr.rvs.mclibrary.bukkit.event;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 * Created by JunHyeong Lim on 2018-03-15
 */
public class PlayerBlockWalkEvent extends PlayerWalkEvent {
    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public PlayerBlockWalkEvent(Player player, Location from, Location to) {
        super(player, from, to);
    }

    public PlayerBlockWalkEvent(PlayerMoveEvent event) {
        super(event);
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
