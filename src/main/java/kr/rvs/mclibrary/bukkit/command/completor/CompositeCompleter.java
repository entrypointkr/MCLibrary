package kr.rvs.mclibrary.bukkit.command.completor;

import kr.rvs.mclibrary.bukkit.command.CommandArguments;
import kr.rvs.mclibrary.bukkit.command.CompositeBase;
import kr.rvs.mclibrary.bukkit.command.TabCompletable;
import kr.rvs.mclibrary.bukkit.player.CommandSenderWrapper;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Junhyeong Lim on 2017-09-26.
 */
public class CompositeCompleter extends CompositeBase<TabCompletable, CompositeCompleter> implements TabCompletable {
    @Override
    public List<String> tabComplete(CommandSenderWrapper wrapper, CommandArguments args) {
        String arg = args.peekFirst();
        return containsKey(arg) ?
                get(args.pollFirst()).tabComplete(wrapper, args) :
                keySet().stream()
                        .filter(key -> key.startsWith(arg))
                        .collect(Collectors.toList());
    }
}
