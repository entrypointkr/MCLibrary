package kr.rvs.mclibrary.bukkit.wizard;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.function.Consumer;

/**
 * Created by Junhyeong Lim on 2017-11-01.
 */
public class ChatWizard extends ListenerWizard<String> {
    public ChatWizard(Player player, Consumer<String> callback) {
        super(player, callback);
    }

    @Override
    protected void process() {
        registerListener(new Listener() {
            @EventHandler
            public void onChat(AsyncPlayerChatEvent event) {
                if (event.getPlayer().equals(getPlayer())) {
                    event.setCancelled(true);
                    release(event.getMessage());
                }
            }
        });
    }
}
