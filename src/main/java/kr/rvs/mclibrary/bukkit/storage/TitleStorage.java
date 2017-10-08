package kr.rvs.mclibrary.bukkit.storage;

import org.bukkit.entity.Player;

/**
 * Created by Junhyeong Lim on 2017-10-09.
 */
public class TitleStorage extends ReplaceableStoorage<TitleStorage> {
    private final String title;
    private final String subTitle;
    private final int fadeIn;
    private final int stay;
    private final int fadeOut;

    public TitleStorage(String title, String subTitle, int fadeIn, int stay, int fadeOut) {
        this.title = title;
        this.subTitle = subTitle;
        this.fadeIn = fadeIn;
        this.stay = stay;
        this.fadeOut = fadeOut;
    }

    public void send(Player player) {
        player.sendTitle(formatting(title), formatting(subTitle), fadeIn, stay, fadeOut);
    }
}
