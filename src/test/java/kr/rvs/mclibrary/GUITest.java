package kr.rvs.mclibrary;

import kr.rvs.mclibrary.bukkit.inventory.gui.GUI;
import kr.rvs.mclibrary.bukkit.inventory.gui.GUISignatureAdapter;
import kr.rvs.mclibrary.struct.Injector;
import kr.rvs.mclibrary.struct.ItemFactory;
import kr.rvs.mclibrary.struct.MockFactory;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
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
                .item(ItemFactory.createRandomItems())).open(player);
    }
}
