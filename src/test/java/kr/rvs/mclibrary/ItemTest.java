package kr.rvs.mclibrary;

import kr.rvs.mclibrary.bukkit.item.ItemBuilder;
import kr.rvs.mclibrary.bukkit.item.ItemWrapper;
import kr.rvs.mclibrary.struct.Injector;
import kr.rvs.mclibrary.struct.MockFactory;
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
                .display("Test diamond")
                .loreWithLineBreak(
                        "Pneumonoultramicroscopic",
                        "Silicovolcanoconiosis"
                );

        ItemStack item = builder.build();
        assertEquals(item.getItemMeta().getDisplayName(), "Test diamond");
        System.out.println(item.getItemMeta().getLore().toString());

        ItemWrapper wrapper = builder.buildAndWrapping();
        wrapper.setName("Test diamond B");
        assertEquals(wrapper.get().getItemMeta().getDisplayName(), "Test diamond B");
    }
}
