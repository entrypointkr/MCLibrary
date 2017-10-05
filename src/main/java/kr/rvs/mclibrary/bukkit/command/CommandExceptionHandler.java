package kr.rvs.mclibrary.bukkit.command;

import kr.rvs.mclibrary.bukkit.player.CommandSenderWrapper;

/**
 * Created by Junhyeong Lim on 2017-09-27.
 */
public interface CommandExceptionHandler {
    default void init(CommandAdaptor adaptor) {
    }

    void handle(Exception ex, CommandSenderWrapper wrapper, CommandArguments args);
}
