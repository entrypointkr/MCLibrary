package kr.rvs.mclibrary.util.bukkit.inventory.factory;

import kr.rvs.mclibrary.util.bukkit.inventory.gui.GUI;
import kr.rvs.mclibrary.util.bukkit.inventory.gui.GUIContents;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import java.util.Objects;

/**
 * Created by Junhyeong Lim on 2017-08-18.
 */
public abstract class InventoryFactory {
    private GUI gui;
    private InventoryType type;
    private String title;
    private Integer size;
    private GUIContents contents;

    public void initialize(GUI gui, InventoryType type, String title, int size, GUIContents contents) {
        this.gui = gui;
        this.type = type;
        this.title = title;
        this.size = size;
        this.contents = contents;
    }

    public abstract Inventory create(HumanEntity viewer);

    protected Inventory createDefaultInventory() {
        return getType() == InventoryType.CHEST ?
                Bukkit.createInventory(null, getSize(), getTitle()) :
                Bukkit.createInventory(null, getType(), getTitle());
    }

    public boolean isSimilar(Inventory inv) {
        return inv != null &&
                inv.getSize() == getSize() &&
                inv.getType() == getType() &&
                inv.getTitle().equals(getTitle());
    }

    public GUI getGui() {
        return gui;
    }

    public InventoryType getType() {
        return Objects.requireNonNull(type);
    }

    public String getTitle() {
        return Objects.requireNonNull(title);
    }

    public int getSize() {
        return Objects.requireNonNull(size);
    }

    public GUIContents getContents() {
        return Objects.requireNonNull(contents);
    }
}
