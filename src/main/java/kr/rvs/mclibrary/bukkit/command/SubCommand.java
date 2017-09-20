package kr.rvs.mclibrary.bukkit.command;

import kr.rvs.mclibrary.bukkit.player.CommandSenderWrapper;
import kr.rvs.mclibrary.collection.VolatileArrayList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Junhyeong Lim on 2017-09-19.
 */
public interface SubCommand extends CommandExecutable, TabCompletable {
    String args();

    default CommandType type() {
        return CommandType.DEFAULT;
    }

    default String perm() {
        return null;
    }

    default int min() {
        return 0;
    }

    default int max() {
        return Integer.MAX_VALUE;
    }

    @Override
    default List<String> tabComplete(CommandSenderWrapper sender, String label, VolatileArrayList args) throws IllegalArgumentException {
        return new ArrayList<>();
    }
}
