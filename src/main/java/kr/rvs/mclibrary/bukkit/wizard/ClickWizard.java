package kr.rvs.mclibrary.bukkit.wizard;

import kr.rvs.mclibrary.bukkit.MCUtils;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Created by Junhyeong Lim on 2017-10-06.
 */
public class ClickWizard extends Wizard<List<Block>> {
    public static final String COUNT = "%count%";
    public static final String REMAIN_COUNT = "%remain_count";
    private final int count;
    private final String startMessage;
    private final String remainMessage;
    private final String completeMessage;

    public ClickWizard(Player player, int count, String startMessage, String remainMessage, String completeMessage) {
        super(player);
        this.count = count;
        this.startMessage = startMessage;
        this.remainMessage = remainMessage;
        this.completeMessage = completeMessage;
    }

    public ClickWizard(Player player, int count) {
        this(
                player,
                count,
                "&a블럭을 &f" + COUNT + " &a회 클릭하세요.",
                "&f" + REMAIN_COUNT + " &a회 남았습니다.",
                "&a완료했습니다."
        );
    }

    private String formatting(String target, int remainCount) {
        return MCUtils.colorize(target
                .replace(COUNT, String.valueOf(count))
                .replace(REMAIN_COUNT, String.valueOf(remainCount)));
    }

    @Override
    protected void start0(Consumer<List<Block>> callback) {
        getPlayer().sendMessage(formatting(startMessage, count));
        List<Block> data = new ArrayList<>(count);
        registerEvents(new Listener() {
            @EventHandler
            public void onClick(PlayerInteractEvent event) {
                Player player = event.getPlayer();
                Block clickedBlock = event.getClickedBlock();
                if (clickedBlock != null && !clickedBlock.isEmpty()) {
                    data.add(clickedBlock);
                    if (data.size() >= count) {
                        callback.accept(data);
                        player.sendMessage(formatting(completeMessage, 0));
                        release(event.getHandlers(), this);
                    } else {
                        player.sendMessage(formatting(remainMessage, count - data.size()));
                    }
                    event.setCancelled(true);
                }
            }
        });
    }
}
