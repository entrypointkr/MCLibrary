package kr.rvs.mclibrary.bukkit.wizard;

import kr.rvs.mclibrary.bukkit.location.Region;
import org.bukkit.entity.Player;

import java.util.function.Consumer;

/**
 * Created by Junhyeong Lim on 2017-11-01.
 */
public class RegionWizard extends Wizard<Region> {
    private final ClickWizard wizard;

    public RegionWizard(Player player, Consumer<Region> callback) {
        super(player, callback);
        this.wizard = new ClickWizard(player, blocks -> {
            callback.accept(new Region(
                    blocks.get(0).getLocation(),
                    blocks.get(1).getLocation()
            ));
            release();
        }, 2);
    }

    @Override
    protected void process() {
        wizard.process();
    }

    @Override
    protected void release() {
        wizard.release();
    }
}
