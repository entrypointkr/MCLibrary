package kr.rvs.mclibrary.bukkit.command;

import kr.rvs.mclibrary.bukkit.player.CommandSenderWrapper;
import kr.rvs.mclibrary.collection.VolatileArrayList;

import java.util.List;

/**
 * Created by Junhyeong Lim on 2017-09-20.
 */
public interface TabCompletable {
    List<String> tabComplete(CommandSenderWrapper sender, String label, VolatileArrayList args) throws IllegalArgumentException;
}
