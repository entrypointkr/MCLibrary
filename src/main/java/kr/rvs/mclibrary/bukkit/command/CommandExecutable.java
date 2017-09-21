package kr.rvs.mclibrary.bukkit.command;

import kr.rvs.mclibrary.bukkit.player.CommandSenderWrapper;
import kr.rvs.mclibrary.collection.VolatileArrayList;

/**
 * Created by Junhyeong Lim on 2017-09-20.
 */
public interface CommandExecutable {
    /**
     * @param wrapper CommandSender
     * @param cmd BaseCommand
     * @param label Command label
     * @param args Command arguments
     */
    void execute(CommandSenderWrapper wrapper, BaseCommand cmd, String label, VolatileArrayList args);
}
