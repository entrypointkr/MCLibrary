package kr.rvs.mclibrary.bukkit.command;

import kr.rvs.mclibrary.bukkit.player.CommandSenderWrapper;

import java.util.List;

/**
 * Created by Junhyeong Lim on 2017-09-26.
 */
public interface TabCompletable {
    List<String> tabComplete(CommandSenderWrapper wrapper, CommandArguments args);
}
