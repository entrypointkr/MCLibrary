package kr.rvs.mclibrary;

import com.google.gson.JsonObject;
import kr.rvs.mclibrary.bukkit.inventory.gui.GUISignature;
import kr.rvs.mclibrary.gson.GsonUtils;
import kr.rvs.mclibrary.mock.MockItemMeta;
import kr.rvs.mclibrary.struct.Injector;
import kr.rvs.mclibrary.struct.ItemFactory;
import kr.rvs.mclibrary.struct.MockFactory;
import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;

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
    public void number() {
        JsonObject object = new JsonObject();
        object.addProperty("i", 1);
        object.addProperty("l", 1L);
        object.addProperty("d", 1D);
        object.addProperty("f", 1F);
        Map<String, Object> map = GsonUtils.read(new StringReader(object.toString()), Map.class);
        assertTrue(map.get("i") instanceof Integer);
        assertTrue(map.get("i") instanceof Integer);
        assertTrue(map.get("d") instanceof Double);
        assertTrue(map.get("f") instanceof Double);
    }

    @Test
    public void guiSignature() {
        ItemStack itemA = ItemFactory.createRandomItem();
        ItemStack itemB = ItemFactory.createRandomItem();
        GUISignature signature = new GUISignature(InventoryType.CHEST)
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

        GUISignature deserialized = GsonUtils.read(new StringReader(writer.toString()), GUISignature.class);
        assertEquals(signature, deserialized);
    }

    @Test
    public void location() {
        Location location = new Location(MockFactory.createMockWorld(), 0, 0, 0);
        StringWriter writer = new StringWriter();
        GsonUtils.write(writer, location, AssertionError::new);

        Location convert = GsonUtils.read(new StringReader(writer.toString()), Location.class);
        assertTrue(location.getWorld().getName().equals(convert.getWorld().getName()));
        convert.setWorld(location.getWorld());
        assertEquals(location, convert);
    }
}
