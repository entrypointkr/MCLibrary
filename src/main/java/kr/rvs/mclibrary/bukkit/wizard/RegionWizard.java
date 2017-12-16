package kr.rvs.mclibrary.bukkit.wizard;

import kr.rvs.mclibrary.bukkit.location.Region;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Created by Junhyeong Lim on 2017-12-16.
 */
public class RegionWizard extends DelegateWizard<Region, Block> {
    private final Player player;
    private Function<Block, String> messageFunction = block -> {
        Location loc = block.getLocation();
        return String.format("&c[ 도우미 ] &ex: %d, y: %d, z: %d &f를 클릭했습니다.",
                loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
    };

    public RegionWizard(Player player) {
        super(new ClickWizard(player));
        this.player = player;
    }

    public RegionWizard message(Function<Block, String> messageFunction) {
        this.messageFunction = messageFunction;
        return this;
    }

    @Override
    protected void process(Consumer<Region> callback) {
        List<Block> blocks = new ArrayList<>(2);
        delegateProcess(block -> {
            blocks.add(block);
            if (blocks.size() >= 2) {
                callback.accept(new Region(
                        blocks.get(0).getLocation(), blocks.get(1).getLocation()
                ));
            } else {
                player.sendMessage(messageFunction.apply(block));
            }
        });
    }
}
