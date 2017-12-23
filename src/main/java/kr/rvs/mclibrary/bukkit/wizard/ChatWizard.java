package kr.rvs.mclibrary.bukkit.wizard;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.function.Consumer;

/**
 * Created by Junhyeong Lim on 2017-12-16.
 */
public class ChatWizard extends ListenerWizard<String> {
    private final Player player;

    public ChatWizard(Player player) {
        this.player = player;
    }

    @Override
    protected Listener listener(Consumer<String> callback) {
        return new Listener() {
            @EventHandler
            public void onChat(AsyncPlayerChatEvent event) {
                if (event.getPlayer().equals(player)) {
                    callback.accept(event.getMessage());
                }
            }
        };
    }
}
