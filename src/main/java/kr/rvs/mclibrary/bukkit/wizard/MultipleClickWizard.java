package kr.rvs.mclibrary.bukkit.wizard;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class MultipleClickWizard extends DelegateWizard<Block, List<Block>> {
    private final int count;

    public MultipleClickWizard(Player player, int count, Function<Block, String> clickMessageFunction) {
        super(new ClickWizard(player, clickMessageFunction));
        this.count = count;
    }

    public MultipleClickWizard(Player player, int count) {
        this(player, count, ClickWizard.CLICK_MESSAGE_FUNCTION);
    }

    @Override
    protected Consumer<Block> processor(Consumer<List<Block>> callback) {
        List<Block> blocks = new ArrayList<>(count);
        return block -> {
            blocks.add(block);
            if (blocks.size() >= count) {
                callback.accept(blocks);
            }
        };
    }
}
