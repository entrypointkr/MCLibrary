package kr.rvs.mclibrary.bukkit.wizard;

import kr.rvs.mclibrary.bukkit.MCUtils;
import kr.rvs.mclibrary.bukkit.location.LocationUtils;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Junhyeong Lim on 2017-10-06.
 */
public class ClickWizard extends Wizard<List<Block>, List<Block>> {
    public static final String COUNT = "%count%";
    public static final String REMAIN_COUNT = "%remain_count";
    public static final String LOCATION = "%location%";
    private final String remainMessage;
    private final int count;
    private int remainCount = 0;
    private Location lastLocation = null;

    public ClickWizard(Player player, String startMessage, String completeMessage, String remainMessage, int count) {
        super(player, new ArrayList<>(), startMessage, completeMessage);
        this.remainMessage = remainMessage;
        this.count = count;
        this.remainCount = count;
    }

    public ClickWizard(Player player, int count) {
        this(
                player,
                "&a블럭을 &f" + COUNT + " &a회 클릭하세요.",
                "&a완료했습니다.",
                String.format("&f%s &a회 남았습니다 (%s)", REMAIN_COUNT, LOCATION),
                count
        );
    }

    private String formatting(String target, int remainCount, Vector vector) {
        return MCUtils.colorize(target
                .replace(COUNT, String.valueOf(count))
                .replace(REMAIN_COUNT, String.valueOf(remainCount))
                .replace(LOCATION, LocationUtils.toString(vector)));
    }

    @Override
    protected void process(List<Block> data) {
        registerEvents(new Listener() {
            @EventHandler
            public void onClick(PlayerInteractEvent event) {
                if (event.getHand() != EquipmentSlot.HAND)
                    return;

                Player player = event.getPlayer();
                Block clickedBlock = event.getClickedBlock();
                if (clickedBlock != null && !clickedBlock.isEmpty()) {
                    data.add(clickedBlock);
                    remainCount = count - data.size();
                    lastLocation = clickedBlock.getLocation();
                    if (remainCount <= 0) {
                        release(data);
                        event.getHandlers().unregister(this);
                    } else {
                        player.sendMessage(messageCaught(remainMessage));
                    }
                    event.setCancelled(true);
                }
            }
        });
    }

    @Override
    protected String messageCaught(String message) {
        return formatting(message, remainCount, lastLocation != null ? lastLocation.toVector() : null);
    }
}
