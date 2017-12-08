package kr.rvs.mclibrary;

import kr.rvs.mclibrary.bukkit.item.ItemBuilder;
import kr.rvs.mclibrary.bukkit.item.ItemWrapper;
import kr.rvs.mclibrary.bukkit.item.MaterialAndData;
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
        assertEquals(wrapper.getHandle().getItemMeta().getDisplayName(), "Test diamond B");
    }

    @Test
    public void matAndDataTest() {
        ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 14);
        MaterialAndData data = MaterialAndData.parse("160:14");
        assertTrue(data.isMatch(item));
        assertTrue(item.isSimilar(data.createItem()));
        assertFalse(MaterialAndData.parse("160").isMatch(item));
    }
}
