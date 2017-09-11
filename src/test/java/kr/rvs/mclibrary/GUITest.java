package kr.rvs.mclibrary;

import kr.rvs.mclibrary.bukkit.inventory.gui.GUI;
import kr.rvs.mclibrary.bukkit.inventory.gui.GUISignatureAdapter;
import kr.rvs.mclibrary.struct.Injector;
import kr.rvs.mclibrary.struct.MockFactory;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.junit.Test;

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
    public void generalTest() {
        Player player = MockFactory.createPlayer();

        new GUI(new GUISignatureAdapter(InventoryType.CHEST)
                .title("GUI")
                .item(createRandomItems())).open(player);
    }

    private ItemStack[] createRandomItems() {
        ItemStack[] items = new ItemStack[random.nextInt(27)];
        for (int i = 0; i < items.length; i++) {
            items[i] = createRandomItem();
        }
        return items;
    }

    @Test
    public void pagingTest() {

    }

    private ItemStack createRandomItem() {
        Material[] materials = Material.values();
        return new ItemStack(materials[random.nextInt(materials.length)]);
    }
}
