package kr.rvs.mclibrary;

import kr.rvs.mclibrary.util.bukkit.inventory.GUIHelper;
import org.bukkit.event.inventory.InventoryType;
import org.junit.Test;

/**
 * Created by Junhyeong Lim on 2017-07-26.
 */
public class GUITest {
    @Test
    public void onGuiTest() {
        GUIHelper helper = new GUIHelper(InventoryType.CHEST);
    }
}
