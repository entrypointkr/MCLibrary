package kr.rvs.mclibrary.util.bukkit.inventory;

import org.apache.commons.lang.Validate;
import org.bukkit.event.inventory.InventoryType;

/**
 * Created by Junhyeong Lim on 2017-07-26.
 */
public class GUIHelper {
    private final InventoryType type;
    private int size = 9;
    private String title;

    public GUIHelper(InventoryType type) {
        Validate.notNull(type);

        this.type = type;
        this.title = type.getDefaultTitle();
    }

    public GUIHelper setSize(int size) {
        Validate.isTrue(size % 9 != 0);

        this.size = size;
        return this;
    }

    public GUIHelper setTitle(String title) {
        Validate.notNull(title);

        this.title = title;
        return this;
    }
}
