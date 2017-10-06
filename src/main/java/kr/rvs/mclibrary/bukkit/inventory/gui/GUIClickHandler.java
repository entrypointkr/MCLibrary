package kr.rvs.mclibrary.bukkit.inventory.gui;

import kr.rvs.mclibrary.bukkit.inventory.event.GUIClickEvent;

/**
 * Created by Junhyeong Lim on 2017-10-06.
 */
public interface GUIClickHandler {
    void click(GUIEvent<GUIClickEvent> event);
}
