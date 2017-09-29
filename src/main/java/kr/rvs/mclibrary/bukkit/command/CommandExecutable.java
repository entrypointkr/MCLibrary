package kr.rvs.mclibrary.bukkit.command;

import kr.rvs.mclibrary.bukkit.player.CommandSenderWrapper;

/**
 * Created by Junhyeong Lim on 2017-09-25.
 */
public interface CommandExecutable {
    void execute(CommandSenderWrapper wrapper, CommandArguments args);
}
