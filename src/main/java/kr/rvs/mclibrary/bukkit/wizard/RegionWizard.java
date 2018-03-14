package kr.rvs.mclibrary.bukkit.wizard;

import kr.rvs.mclibrary.bukkit.location.Region;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Created by Junhyeong Lim on 2017-12-16.
 */
public class RegionWizard extends DelegateWizard<List<Block>, Region> {
    public RegionWizard(Player player, Function<Block, String> messageFunction) {
        super(new MultipleClickWizard(player, 2, messageFunction));
    }

    public RegionWizard(Player player) {
        super(new MultipleClickWizard(player, 2));
    }

    @Override
    protected Consumer<List<Block>> processor(Consumer<Region> callback) {
        return blocks -> callback.accept(Region.of(
                blocks.get(0).getLocation(),
                blocks.get(1).getLocation()
        ));
    }
}
