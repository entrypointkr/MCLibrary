package kr.rvs.mclibrary.bukkit.inventory.gui.handler;

import kr.rvs.mclibrary.bukkit.inventory.event.GUIClickEvent;
import kr.rvs.mclibrary.bukkit.inventory.gui.GUIEvent;
import kr.rvs.mclibrary.bukkit.storage.SoundStorage;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

import java.util.Collection;

/**
 * Created by Junhyeong Lim on 2017-10-11.
 */
public class SoundPlayingHandler extends ClickHandler {
    private final SoundStorage sound;

    public SoundPlayingHandler(SoundStorage sound, Integer... slots) {
        super(slots);
        this.sound = sound;
    }

    public SoundPlayingHandler(Collection<Integer> slots, SoundStorage sound) {
        super(slots);
        this.sound = sound;
    }

    @Override
    public void click(GUIEvent<GUIClickEvent> event) {
        HumanEntity clicker = event.getEvent().getWhoClicked();
        if (clicker instanceof Player)
            sound.play((Player) clicker);
    }
}
