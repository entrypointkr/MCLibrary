package kr.rvs.mclibrary;

import kr.rvs.mclibrary.util.Injector;
import kr.rvs.mclibrary.util.MockFactory;
import kr.rvs.mclibrary.util.bukkit.command.item.ItemBuilder;
import kr.rvs.mclibrary.util.bukkit.command.item.ItemWrapper;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Junhyeong Lim on 2017-07-27.
 */
public class ItemTest extends Assert {
    @Before
    public void dependencyInject() {
        Injector.injectServer(MockFactory.createMockServer());
    }

    @Test
    public void itemTest() {
        ItemBuilder builder = new ItemBuilder(Material.DIAMOND)
                .name("Test diamond")
                .lore(
                        "Test",
                        "Lore"
                );

        ItemStack item = builder.build();
        assertEquals(item.getItemMeta().getDisplayName(), "Test diamond");

        ItemWrapper wrapper = builder.buildAndWrapping();
        wrapper.setName("Test diamond B");
        assertEquals(wrapper.getItemStack().getItemMeta().getDisplayName(), "Test diamond B");
    }
}
