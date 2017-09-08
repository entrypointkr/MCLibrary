package kr.rvs.mclibrary;

import kr.rvs.mclibrary.util.Injector;
import kr.rvs.mclibrary.util.MockFactory;
import kr.rvs.mclibrary.util.bukkit.inventory.gui.GUI;
import kr.rvs.mclibrary.util.bukkit.inventory.gui.GUIBuilder;
import kr.rvs.mclibrary.util.bukkit.inventory.gui.GUIContents;
import kr.rvs.mclibrary.util.bukkit.inventory.gui.GUIHandler;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.junit.Test;

import java.util.Collection;
import java.util.Random;

/**
 * Created by Junhyeong Lim on 2017-09-08.
 */
public class GUITest {
    private final Random random = new Random();

    @Test
    public void inject() {
        Injector.injectServer(MockFactory.createMockServer());
    }

    @Test
    public void test() {
        Player player = MockFactory.createPlayer();
        ItemStack[] items = new ItemStack[random.nextInt(27)];

        for (int i = 0; i < items.length; i++) {
            items[i] = createItem();
        }

        new GUIBuilder(InventoryType.CHEST)
                .title("GUIAdapter(GUIBuilder)")
                .lineSize(6)
                .contents(new GUIContents().item(items))
                .build().open(player);

        new GUI() {
            @Override
            public String title() {
                return "Extends GUI";
            }

            @Override
            public int lineSize() {
                return 6;
            }

            @Override
            public Collection<GUIHandler> handlers() {
                return null;
            }

            @Override
            public GUIContents contents() {
                return new GUIContents().item(items);
            }
        }.open(player);
    }

    ItemStack createItem() {
        Material[] materials = Material.values();
        return new ItemStack(materials[random.nextInt(materials.length)]);
    }
}
