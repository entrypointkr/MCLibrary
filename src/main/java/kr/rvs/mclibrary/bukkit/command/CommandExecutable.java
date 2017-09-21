package kr.rvs.mclibrary.bukkit.command;

import kr.rvs.mclibrary.bukkit.player.CommandSenderWrapper;
import kr.rvs.mclibrary.collection.VolatileArrayList;

/**
 * Created by Junhyeong Lim on 2017-09-20.
 */
public interface CommandExecutable {
    void execute(CommandSenderWrapper wrapper, String label, VolatileArrayList args);
}
