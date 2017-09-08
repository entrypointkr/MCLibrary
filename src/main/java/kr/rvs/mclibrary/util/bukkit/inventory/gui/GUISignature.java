package kr.rvs.mclibrary.util.bukkit.inventory.gui;

import kr.rvs.mclibrary.util.bukkit.inventory.factory.InventoryFactory;
import org.bukkit.event.inventory.InventoryType;

import java.util.Collection;

/**
 * Created by Junhyeong Lim on 2017-09-07.
 */
public interface GUISignature {
    InventoryType type();

    int size();

    String title();

    InventoryFactory factory();

    Collection<GUIHandler> handlers();

    GUIContents contents();
}
