package kr.rvs.mclibrary.bukkit.wizard;

import kr.rvs.mclibrary.bukkit.MCUtils;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Created by Junhyeong Lim on 2017-12-16.
 */
public class ClickWizard extends ListenerWizard<Block> {
    public static final Function<Block, String> CLICK_MESSAGE_FUNCTION = block -> {
        Location loc = block.getLocation();
        return String.format("&c&l[ 도우미 ] &ex: %d, y: %d, z: %d type: %s &f를 클릭했습니다.",
                loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), block.getType());
    };
    private final Player player;
    private final Function<Block, String> messageFunc;

    public ClickWizard(Player player, Function<Block, String> messageFunc) {
        this.player = player;
        this.messageFunc = messageFunc;
    }

    public ClickWizard(Player player) {
        this(player, CLICK_MESSAGE_FUNCTION);
    }

    @Override
    protected Listener listener(Consumer<Block> callback) {
        return new Listener() {
            @EventHandler
            public void onClick(PlayerInteractEvent event) {
                Player clicker = event.getPlayer();
                EquipmentSlot hand = event.getHand();
                Block block = event.getClickedBlock();

                if (block != null && clicker.equals(player)
                        && (!MCUtils.isOffHandSupport() || hand == EquipmentSlot.HAND)) {
                    clicker.sendMessage(MCUtils.colorize(messageFunc.apply(block)));
                    callback.accept(block);
                    event.setCancelled(true);
                }
            }
        };
    }
}
