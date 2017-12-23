package kr.rvs.mclibrary.bukkit.wizard;

import kr.rvs.mclibrary.bukkit.MCUtils;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import java.util.function.Consumer;

/**
 * Created by Junhyeong Lim on 2017-12-16.
 */
public class ClickWizard extends ListenerWizard<Block> {
    private final Player player;

    public ClickWizard(Player player) {
        this.player = player;
    }

    @Override
    protected Listener listener(Consumer<Block> callback) {
        return new Listener() {
            @EventHandler
            public void onClick(PlayerInteractEvent event) {
                if (event.getPlayer().equals(player)
                        && !MCUtils.isOffHandSupport() || event.getHand() == EquipmentSlot.HAND) {
                    callback.accept(event.getClickedBlock());
                    event.setCancelled(true);
                }
            }
        };
    }
}
