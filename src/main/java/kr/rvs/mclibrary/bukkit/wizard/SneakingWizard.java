package kr.rvs.mclibrary.bukkit.wizard;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import java.util.function.Consumer;

/**
 * Created by Junhyeong Lim on 2017-12-17.
 */
public class SneakingWizard extends ListenerWizard<Player> {
    private final Player player;

    public SneakingWizard(Player player) {
        this.player = player;
    }

    @Override
    public Listener listener(Consumer<Player> callback) {
        return new Listener() {
            @EventHandler
            public void onSneak(PlayerToggleSneakEvent event) {
                if (!event.isSneaking() && player.equals(event.getPlayer())) {
                    callback.accept(event.getPlayer());
                }
            }
        };
    }
}
