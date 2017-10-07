package kr.rvs.mclibrary.bukkit.wizard;

import kr.rvs.mclibrary.bukkit.location.Region;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.function.Consumer;

/**
 * Created by Junhyeong Lim on 2017-10-07.
 */
public class RegionWizard extends Wizard<List<Block>, Region> {
    private final ClickWizard clickWizard;

    public RegionWizard(Player player, ClickWizard wizard) {
        super(player, wizard.getData(), wizard.getStartMessage(), wizard.getCompleteMessage());
        this.clickWizard = wizard;
    }

    public RegionWizard(Player player) {
        this(player, new ClickWizard(player, 2));
    }

    @Override
    public void start(Consumer<Region> callback) throws Exception {
        super.start(callback);
        clickWizard.start(blocks ->
                release(new Region(
                        blocks.get(0).getLocation(),
                        blocks.get(1).getLocation()
                )));
    }

    @Override
    protected void process(List<Block> data) {
        clickWizard.process(data);
    }
}
