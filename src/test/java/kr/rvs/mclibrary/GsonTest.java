package kr.rvs.mclibrary;

import kr.rvs.mclibrary.bukkit.inventory.gui.GUISignature;
import kr.rvs.mclibrary.bukkit.inventory.gui.GUISignatureAdapter;
import kr.rvs.mclibrary.gson.GsonUtils;
import kr.rvs.mclibrary.mock.MockItemMeta;
import kr.rvs.mclibrary.struct.Injector;
import kr.rvs.mclibrary.struct.ItemFactory;
import kr.rvs.mclibrary.struct.MockFactory;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.StringReader;
import java.io.StringWriter;

/**
 * Created by Junhyeong Lim on 2017-09-11.
 */
public class GsonTest extends Assert {
    @Before
    public void inject() {
        Injector.injectServer(MockFactory.createMockServer());
        ConfigurationSerialization.registerClass(ItemStack.class);
        ConfigurationSerialization.registerClass(MockItemMeta.class);
    }

    @Test
    public void guiSignature() {
        ItemStack itemA = ItemFactory.createRandomItem();
        ItemStack itemB = ItemFactory.createRandomItem();
        GUISignature signature = new GUISignatureAdapter(InventoryType.CHEST)
                .title("Serialize Test")
                .size(18)
                .addHandlerIndexes(1, 2, 3)
                .item(itemA, 0)
                .item(itemA, 1)
                .item(ItemFactory.createRandomItem(), 2)
                .item(itemB, 3)
                .item(itemB, 4)
                .item(ItemFactory.createRandomItem(), 5);
        StringWriter writer = new StringWriter();
        GsonUtils.write(writer, signature, e -> {
            throw new AssertionError(e);
        });

        System.out.println("Serialized: " + writer.toString());
        GUISignature deserialized = GsonUtils.read(new StringReader(writer.toString()), GUISignatureAdapter.class);
        System.out.println("Deserialized: " + deserialized);
        assertEquals(signature, deserialized);
    }
}
