package kr.rvs.mclibrary.bukkit.item;


import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Junhyeong Lim on 2017-08-27.
 */
public class MaterialAndData {
    private final Material material;
    private final short data;

    @SuppressWarnings("deprecation")
    public static MaterialAndData ofString(String idAndData) {
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

    public ItemBuilder createBuilder() {
        return new ItemBuilder(createItem());
    }

    public Material getMaterial() {
        return material;
    }

    public short getData() {
        return data;
    }
}
