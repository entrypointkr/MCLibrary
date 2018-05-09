package kr.rvs.mclibrary.bukkit.item;


import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;

/**
 * Created by Junhyeong Lim on 2017-08-27.
 */
@SuppressWarnings("deprecation")
public class MaterialAndData {
    private final Material material;
    private final short data;

    public static MaterialAndData parse(String idAndData) {
        String[] splited = idAndData.split(":");
        int id = Integer.parseInt(splited[0]);
        Material material = Material.getMaterial(id);
        return new MaterialAndData(material, splited.length >= 2
                ? Short.parseShort(splited[1]) : 0);
    }

    public MaterialAndData(Material material, short data) {
        this.material = material;
        this.data = data;
    }

    public MaterialAndData(Material material, int data) {
        this(material, (short) data);
    }

    public MaterialAndData(Material material) {
        this(material, 0);
    }

    public boolean isMatch(ItemStack item) {
        return item != null && item.getType() == material
                && item.getDurability() == data;
    }

    public ItemStack createItem() {
        return new ItemStack(material, 1, data);
    }

    public void applyToBlock(Block block) {
        BlockState state = block.getState();
        block.setType(getMaterial());
        state.setRawData((byte) getData());
        state.update();
    }

    public ItemBuilder createBuilder() {
        return new ItemBuilder(createItem());
    }

    public Material getMaterial() {
        return material;
    }

    public short getData() {
        return data;
    }

    @SuppressWarnings({"unchecked", "deprecation"})
    public static class Adapter extends TypeAdapter<MaterialAndData> {
        @Override
        public void write(JsonWriter out, MaterialAndData value) throws IOException {
            if (value != null) {
                out.value(value.getMaterial().getId() + ":" + value.getData());
            } else {
                out.nullValue();
            }
        }

        @Override
        public MaterialAndData read(JsonReader in) throws IOException {
            return in.hasNext() && in.peek() == JsonToken.STRING
                    ? parse(in.nextString())
                    : null;
        }
    }
}
