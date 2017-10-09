package kr.rvs.mclibrary.bukkit.storage;

import org.bukkit.entity.Player;

/**
 * Created by Junhyeong Lim on 2017-10-09.
 */
public class TitleStorage extends ReplaceableStoorage<TitleStorage> {
    private String title;
    private String subTitle;
    private int fadeIn;
    private int stay;
    private int fadeOut;

    public TitleStorage(String title, String subTitle, int fadeIn, int stay, int fadeOut) {
        this.title = title;
        this.subTitle = subTitle;
        this.fadeIn = fadeIn;
        this.stay = stay;
        this.fadeOut = fadeOut;
    }

    public void send(Player player) {
        if (player != null)
            player.sendTitle(formatting(title), formatting(subTitle), fadeIn, stay, fadeOut);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public int getFadeIn() {
        return fadeIn;
    }

    public void setFadeIn(int fadeIn) {
        this.fadeIn = fadeIn;
    }

    public int getStay() {
        return stay;
    }

    public void setStay(int stay) {
        this.stay = stay;
    }

    public int getFadeOut() {
        return fadeOut;
    }

    public void setFadeOut(int fadeOut) {
        this.fadeOut = fadeOut;
    }
}
