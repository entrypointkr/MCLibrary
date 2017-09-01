package kr.rvs.mclibrary;

import kr.rvs.mclibrary.util.Injector;
import kr.rvs.mclibrary.util.MockFactory;
import kr.rvs.mclibrary.util.bukkit.inventory.GUI;
import kr.rvs.mclibrary.util.bukkit.inventory.GUIBuilder;
import kr.rvs.mclibrary.util.bukkit.inventory.GUIHandler;
import kr.rvs.mclibrary.util.bukkit.item.ItemBuilder;
import kr.rvs.mclibrary.util.general.SpeedTester;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Junhyeong Lim on 2017-09-01.
 */
public class PerformanceTest {
    @Before
    public void dependencyInject() {
        Injector.injectServer(MockFactory.createMockServer());
    }

    @Test
    public void test() {
        ItemStack item = new ItemStack(Material.STONE);

        new SpeedTester(10, false)
                .addRunnable("item", () -> {
                    new ItemBuilder(item)
                            .display("a b c")
                            .addReplacements(
                                    "a", "1",
                                    "b", "2",
                                    "c", "3"
                            )
                            .build();
                })
                .addRunnable("gui", () -> {
                    GUI gui = new GUIBuilder(InventoryType.CHEST)
                            .title("test")
                            .lineSize(6)
                            .item(item, item)
                            .handler(new GUIHandler() {
                                })
                            .build();
                    gui.open(MockFactory.createPlayer());
                })
                .start();
    }
}
