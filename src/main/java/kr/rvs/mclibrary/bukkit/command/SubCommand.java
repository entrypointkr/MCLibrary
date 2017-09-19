package kr.rvs.mclibrary.bukkit.command;

import kr.rvs.mclibrary.bukkit.player.CommandSenderWrapper;
import kr.rvs.mclibrary.collection.VolatileArrayList;

/**
 * Created by Junhyeong Lim on 2017-09-19.
 */
public interface SubCommand {
    String args();

    default int min() {
        return Integer.MAX_VALUE;
    }

    default int max() {
        return 0;
    }

    void execute(CommandSenderWrapper sender, String label, VolatileArrayList args);
}
