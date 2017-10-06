package kr.rvs.mclibrary.bukkit.inventory.gui.handler;

import kr.rvs.mclibrary.bukkit.inventory.event.GUIClickEvent;
import kr.rvs.mclibrary.bukkit.inventory.gui.GUIClickHandler;
import kr.rvs.mclibrary.bukkit.inventory.gui.GUIEvent;

/**
 * Created by Junhyeong Lim on 2017-10-06.
 */
public class DelegateClickHandler extends ClickHandler {
    private final GUIClickHandler handler;

    public DelegateClickHandler(GUIClickHandler handler, Integer... slots) {
        super(slots);
        this.handler = handler;
    }

    @Override
    public void click(GUIEvent<GUIClickEvent> event) {
        handler.click(event);
    }
}
