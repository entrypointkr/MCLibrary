package kr.rvs.mclibrary.bukkit.item;


import org.bukkit.Material;

/**
 * Created by Junhyeong Lim on 2017-08-27.
 */
public class MaterialAndData {
    private final Material material;
    private final short data;

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

    public Material getMaterial() {
        return material;
    }

    public short getData() {
        return data;
    }
}
