package kr.rvs.mclibrary.bukkit.inventory.newgui.handler;

import org.bukkit.event.inventory.InventoryEvent;

/**
 * Created by JunHyeong Lim on 2018-05-13
 */
public class TypeHandler<T extends InventoryEvent> implements GUIHandler {
    private final Class<T> type;
    private final GUIHandler<T> callback;

    public TypeHandler(Class<T> type, GUIHandler<T> callback) {
        this.type = type;
        this.callback = callback;
    }

    @Override
    public void onEvent(InventoryEvent event) {
        if (type.isInstance(event)) {
            callback.onEvent(type.cast(event));
        }
    }
}
