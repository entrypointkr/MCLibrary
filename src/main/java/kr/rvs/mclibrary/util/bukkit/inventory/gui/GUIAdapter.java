package kr.rvs.mclibrary.util.bukkit.inventory.gui;

import kr.rvs.mclibrary.util.bukkit.inventory.factory.InventoryFactory;
import org.bukkit.event.inventory.InventoryType;

import java.util.Collection;

/**
 * Created by Junhyeong Lim on 2017-09-07.
 */
public class GUIAdapter extends GUI {
    private final InventoryType type;
    private final String title;
    private final int size;
    private final GUIContents contents;
    private final Collection<GUIHandler> handlers;
    private final InventoryFactory factory;

    public GUIAdapter(InventoryType type, String title, int size, GUIContents contents, Collection<GUIHandler> handlers, InventoryFactory factory) {
        this.type = type;
        this.title = title;
        this.size = size;
        this.contents = contents;
        this.handlers = handlers;
        this.factory = factory;

        addHandlers(handlers);
    }

    @Override
    public InventoryType type() {
        return type;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public String title() {
        return title;
    }

    @Override
    public InventoryFactory factory() {
        return factory;
    }

    @Override
    public Collection<GUIHandler> handlers() {
        return handlers;
    }

    @Override
    public GUIContents contents() {
        return contents;
    }
}
