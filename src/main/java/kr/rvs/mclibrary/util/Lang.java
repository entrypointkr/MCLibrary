package kr.rvs.mclibrary.util;

import org.bukkit.ChatColor;

/**
 * Created by Junhyeong Lim on 2017-07-26.
 */
public enum Lang {
    PREFIX("[MCLibrary] ")
    ;
    private String content;

    Lang(String content) {
        this.content = content;
    }

    public Lang stripColor(boolean strip) {
        if (strip)
            content = ChatColor.stripColor(content);

        return this;
    }

    @Override
    public String toString() {
        return content;
    }
}
