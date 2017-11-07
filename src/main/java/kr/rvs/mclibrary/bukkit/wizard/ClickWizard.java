package kr.rvs.mclibrary.bukkit.wizard;

import kr.rvs.mclibrary.bukkit.MCUtils;
import kr.rvs.mclibrary.bukkit.event.SafePlayerInteractEvent;
import kr.rvs.mclibrary.bukkit.location.LocationUtils;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Created by Junhyeong Lim on 2017-11-01.
 */
public class ClickWizard extends ListenerWizard<List<Block>> {
    private final List<Block> data = new ArrayList<>();
    private final int count;
    private final String selectMessage;
    private final String endMessage;

    public ClickWizard(Player player, Consumer<List<Block>> callback, int count, String selectMessage, String endMessage) {
        super(player, callback);
        this.count = count;
        this.selectMessage = MCUtils.colorize(selectMessage);
        this.endMessage = MCUtils.colorize(endMessage);
    }

    public ClickWizard(Player player, Consumer<List<Block>> callback, int count) {
        this(player, callback, count,
                "&f{0} &a회 남았습니다. {1}",
                "&a완료했습니다."
        );
    }

    @Override
    protected void process() {
        registerListener(new Listener() {
            @EventHandler
            public void onClick(SafePlayerInteractEvent e) {
                PlayerInteractEvent event = e.getDelegate();
                if (e.hasGetHandMethod() && event.getHand() != EquipmentSlot.HAND
                        || !event.getPlayer().equals(getPlayer()))
                    return;

                Player player = event.getPlayer();
                Block clickedBlock = event.getClickedBlock();
                if (clickedBlock != null && !clickedBlock.isEmpty()) {
                    data.add(clickedBlock);
                    event.setCancelled(true);
                    if (data.size() >= count) {
                        release(data);
                        player.sendMessage(endMessage);
                    } else {
                        player.sendMessage(MessageFormat.format(selectMessage, count - data.size(),
                                LocationUtils.toString(clickedBlock.getLocation().toVector())));
                    }
                }
            }
        });
    }
}
