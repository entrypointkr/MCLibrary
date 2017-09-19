package kr.rvs.mclibrary.bukkit.command.internal;

import kr.rvs.mclibrary.collection.OptionalHashMap;
import org.bukkit.command.CommandSender;

import java.util.Queue;

/**
 * Created by Junhyeong Lim on 2017-09-19.
 */
public class CompositeCommand implements ICommand {
    private final OptionalHashMap<String, ICommand> commandMap = new OptionalHashMap<>();

    @Override
    public void execute(CommandSender sender, String label, Queue<String> args) {
        commandMap.getOptional(args.poll()).ifPresent(mcCommand -> mcCommand.execute(sender, label, args));
    }

    public void put(String key, ICommand command) {
        this.commandMap.put(key, command);
    }
}
