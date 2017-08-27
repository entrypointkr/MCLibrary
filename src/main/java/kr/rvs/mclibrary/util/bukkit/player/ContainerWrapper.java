package kr.rvs.mclibrary.util.bukkit.player;

import kr.rvs.mclibrary.util.Static;

import java.lang.reflect.Field;

/**
 * Created by Junhyeong Lim on 2017-08-27.
 */
public class ContainerWrapper {
    private final Object container;

    public ContainerWrapper(Object container) {
        this.container = container;
    }

    public int getWindowId() {
        int ret = -1;
        try {
            Field windowIdField = container.getClass().getField("windowId");
            ret = (int) windowIdField.get(container);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            Static.log(e);
        }

        return ret;
    }
}
