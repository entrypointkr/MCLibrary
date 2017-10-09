package kr.rvs.mclibrary.bukkit.event;

import kr.rvs.mclibrary.MCLibrary;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 * Created by Junhyeong Lim on 2017-10-09.
 */
public class EventCaller implements Listener {
    public static void init(MCLibrary plugin) {
        Bukkit.getPluginManager().registerEvents(new EventCaller(), plugin);
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Location from = e.getFrom();
        Location to = e.getTo();
        if (!from.toVector().equals(to.toVector())) {
            Bukkit.getPluginManager().callEvent(new PlayerWalkEvent(e));
        }
    }
}
